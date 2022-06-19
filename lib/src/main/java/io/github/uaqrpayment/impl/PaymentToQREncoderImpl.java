package io.github.uaqrpayment.impl;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.github.uaqrpayment.api.annotations.LengthType;
import io.github.uaqrpayment.api.annotations.LengthUnit;
import io.github.uaqrpayment.api.annotations.NBUQRFieldAttribute;
import io.github.uaqrpayment.api.PaymentToQREncoder;
import io.github.uaqrpayment.api.UAQRPayable;
import io.github.uaqrpayment.api.exception.InvalidPayable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PaymentToQREncoderImpl implements PaymentToQREncoder {

    @Override
    public BufferedImage encode(final UAQRPayable payable) throws InvalidPayable {
        try (ByteArrayOutputStream payableByteStream = new ByteArrayOutputStream()) {
            List<Method> declaredMethods = orderedProperties();

            for (Method m : declaredMethods) {
                NBUQRFieldAttribute ann = m.getAnnotation(NBUQRFieldAttribute.class);

                // Initial values
                String valueAsString = (String) m.invoke(payable);
                byte[] valuesAsBytes = valueAsString.getBytes(ann.encoding().charset());

                // Step 1: validate content presence
                checkRequiredContentPresentOrThrow(valueAsString, ann);

                // Step 2 Check length boundary
                checkContentLengthOverflowOrThrow(valueAsString, valuesAsBytes, ann);

                // Step 3 Attempt to align fixed length fields
                Pair<String, byte[]> normalizedValues = checkFixedLengthContentAndBackFillIfNeeded(valueAsString, valuesAsBytes, ann);
                // valueAsString = normalizedValues.getLeft(); // uncomment if needed
                valuesAsBytes = normalizedValues.getRight();

                payableByteStream.write(valuesAsBytes);
                payableByteStream.write(UAQRPayable.FIELD_DELIMITER.getBytes(ann.encoding().charset()));
            }
            Map<EncodeHintType, ?> encoderHints = Map.of(EncodeHintType.CHARACTER_SET, "UTF-8");
            BitMatrix qrCodeBitMatrix = new MultiFormatWriter().encode(payableByteStream.toString(), BarcodeFormat.QR_CODE, 300, 300, encoderHints);
            return MatrixToImageWriter.toBufferedImage(qrCodeBitMatrix);
        } catch (IOException | IllegalAccessException | WriterException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private List<Method> orderedProperties() {
        // Filter all methods that marked with @NBUQRFieldAttribute(...)
        // Making sure that order is preserved.
        return Arrays.stream(UAQRPayable.class.getDeclaredMethods())
            .filter(m -> m.isAnnotationPresent(NBUQRFieldAttribute.class)).sorted((a1, a2) -> {
                NBUQRFieldAttribute annotation1 = a1.getAnnotation(NBUQRFieldAttribute.class);
                NBUQRFieldAttribute annotation2 = a2.getAnnotation(NBUQRFieldAttribute.class);
                return Integer.compare(annotation1.fieldNumber(), annotation2.fieldNumber());
            }).collect(Collectors.toList());
    }

    // TODO: add more extensibility in case more values are added.
    private void checkRequiredContentPresentOrThrow(final String valueAsString,
                                                    final NBUQRFieldAttribute relatedAnnotation) throws InvalidPayable {
        switch (relatedAnnotation.requirement()) {
            case MANDATORY:
                if (StringUtils.isEmpty(valueAsString)) {
                    throw new InvalidPayable("Field " + relatedAnnotation.fieldNumber() + " was marked as mandatory but was empty.");
                }
                break;
            case OPTIONAL:
                break;
            case RESERVE:
                if (StringUtils.isNotBlank(valueAsString)) {
                    throw new InvalidPayable("Field " + relatedAnnotation.fieldNumber() + " was marked as reserved but was not empty.");
                }
                break;
            default:
                throw new InvalidPayable("Field " + relatedAnnotation.fieldNumber() + " has requirement value that is not yet supported");
        }
    }

    private void checkContentLengthOverflowOrThrow(final String valueAsString,
                                                   final byte[] valueAsBytes,
                                                   final NBUQRFieldAttribute relatedAnnotation) throws InvalidPayable {
        if (LengthUnit.BYTES.equals(relatedAnnotation.lengthUnit()) && valueAsBytes.length > relatedAnnotation.length()) {
            throw new InvalidPayable("Field " + relatedAnnotation.fieldNumber() + " had content length more than allowed.");
        }

        if (LengthUnit.CHARS.equals(relatedAnnotation.lengthUnit()) && valueAsString.length() > relatedAnnotation.length()) {
            throw new InvalidPayable("Field " + relatedAnnotation.fieldNumber() + " had content length more than allowed.");
        }
    }

    private Pair<String, byte[]> checkFixedLengthContentAndBackFillIfNeeded(final String valueAsString,
                                                                            final byte[] valueAsBytes,
                                                                            final NBUQRFieldAttribute relatedAnnotation) {
        String bufferString = valueAsString;
        byte[] bufferByteArray = valueAsBytes;
        if (LengthType.FIXED.equals(relatedAnnotation.lengthType())) {
            if (LengthUnit.CHARS.equals(relatedAnnotation.lengthUnit()) && valueAsString.length() < relatedAnnotation.length()) {
                int lengthDifference = relatedAnnotation.length() - valueAsString.length();
                bufferString = valueAsString + StringUtils.repeat(UAQRPayable.DEFAULT_FIXED_FIELD_BACKFILL, lengthDifference);
                bufferByteArray = valueAsString.getBytes(relatedAnnotation.encoding().charset());
            }

            if (LengthUnit.BYTES.equals(relatedAnnotation.lengthUnit()) && valueAsBytes.length < relatedAnnotation.length()) {
                bufferByteArray = new byte[relatedAnnotation.length()];
                // Assumption is that spaces at both ISO-646 and UTF-8 are 1 byte long.
                // However, this may cause issues later if exotic encoding is chosen.
                // FIXME: add better filling in case space is encoded with non single byte char.
                Arrays.fill(bufferByteArray, UAQRPayable.DEFAULT_FIXED_FIELD_BACKFILL.getBytes()[0]);
                System.arraycopy(valueAsBytes, 0, bufferByteArray, 0, valueAsBytes.length);
                bufferString = new String(bufferByteArray, relatedAnnotation.encoding().charset());
            }
        }
        return new ImmutablePair<>(bufferString, bufferByteArray);
    }
}
