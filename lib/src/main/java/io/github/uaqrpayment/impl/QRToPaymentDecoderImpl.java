package io.github.uaqrpayment.impl;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import io.github.uaqrpayment.api.QRToPaymentDecoder;
import io.github.uaqrpayment.api.UAQRPayable;
import io.github.uaqrpayment.api.annotations.NBUQRFieldAttribute;
import io.github.uaqrpayment.api.exception.InvalidPayable;
import io.github.uaqrpayment.models.PredefinedPayable;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class QRToPaymentDecoderImpl implements QRToPaymentDecoder {

    @Override
    public UAQRPayable decode(final BufferedImage payableQrCode) throws NotFoundException, InvalidPayable {
        BinaryBitmap bbm = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(payableQrCode)));
        Result result = new MultiFormatReader().decode(bbm);
        String content = result.getText();

        // Number of lines must match to number of properties
        // Warning: string.split("\\R") doesn't work here as it considers last line as EOF and ignores it.
        List<String> lines = new BufferedReader(new StringReader(content)).lines().collect(Collectors.toList());
        int amountOfFields = (int) Arrays.stream(UAQRPayable.class.getDeclaredMethods())
            .filter(m -> m.isAnnotationPresent(NBUQRFieldAttribute.class)).count();

        if (lines.size() != amountOfFields) {
            throw new InvalidPayable("Field amount is inconsistent. Expected " + amountOfFields + " but actual was " + lines.size());
        }

        try {
            Class[] classes = new Class[amountOfFields];
            Arrays.fill(classes, String.class);
            Class payableQrCodeClass = PredefinedPayable.class;
            Constructor constructor = payableQrCodeClass.getConstructor(classes);
            UAQRPayable payable = (PredefinedPayable) constructor.newInstance(lines.toArray());
            return payable;
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
