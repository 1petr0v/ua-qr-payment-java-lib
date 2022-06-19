package io.github.uaqrpayment.api;

import java.awt.image.BufferedImage;

public interface QRToPaymentDecoder {
    UAQRPayable decode(BufferedImage payableQrCode);
}
