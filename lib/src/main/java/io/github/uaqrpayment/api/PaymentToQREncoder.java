package io.github.uaqrpayment.api;

import io.github.uaqrpayment.api.exception.InvalidPayable;

import java.awt.image.BufferedImage;

public interface PaymentToQREncoder {
    BufferedImage encode(UAQRPayable payable) throws InvalidPayable;
}
