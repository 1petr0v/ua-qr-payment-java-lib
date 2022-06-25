package io.github.uaqrpayment.api;

import com.google.zxing.NotFoundException;
import io.github.uaqrpayment.api.exception.InvalidPayableException;

import java.awt.image.BufferedImage;

public interface QRToPaymentDecoder {
    UAQRPayable decode(BufferedImage payableQrCode) throws NotFoundException, InvalidPayableException;
}
