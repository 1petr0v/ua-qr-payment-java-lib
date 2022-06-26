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
import io.github.uaqrpayment.api.exception.InvalidPayableException;
import io.github.uaqrpayment.models.SimplePayable;

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
    public UAQRPayable decode(final BufferedImage payableQrCode) throws NotFoundException, InvalidPayableException {
        BinaryBitmap bbm = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(payableQrCode)));
        Result result = new MultiFormatReader().decode(bbm);
        String content = result.getText();

        // Number of lines must match to number of properties
        // Warning: string.split("\\R") doesn't work here as it considers last line as EOF and ignores it.
        List<String> lines = new BufferedReader(new StringReader(content)).lines().collect(Collectors.toList());
        int amountOfFields = (int) Arrays.stream(UAQRPayable.class.getDeclaredMethods())
            .filter(m -> m.isAnnotationPresent(NBUQRFieldAttribute.class)).count();

        if (lines.size() != amountOfFields) {
            throw new InvalidPayableException("Field amount is inconsistent. Expected " + amountOfFields + " but actual was " + lines.size());
        }

        try {
            Class[] classes = new Class[amountOfFields];
            Arrays.fill(classes, String.class);
            Class<SimplePayable> payableQrCodeClass = SimplePayable.class;
            Constructor<SimplePayable> constructor = payableQrCodeClass.getConstructor(classes);
            UAQRPayable payable = constructor.newInstance(lines.toArray());
            return payable;
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
