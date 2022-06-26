package io.github.uaqrpayment.impl;

import com.google.zxing.NotFoundException;
import io.github.uaqrpayment.api.UAQRPayable;
import io.github.uaqrpayment.api.exception.InvalidPayableException;
import io.github.uaqrpayment.models.SimplePayableFixture;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;

class QRToPaymentDecoderImplTest {

    private static final String GENERIC_PAYABLE_QR_IMAGE_PATH = "/generic.png";
    private static final String QR_IMAGE_WITH_EXTRA_FIELD_PATH = "/invalid-extra-field.png";
    private static final String NOT_QR_CODE_IMAGE_PATH = "/not-qr-code.png";

    private QRToPaymentDecoderImpl objectUnderTest;
    private BufferedImage qrCodeFromFile;

    @BeforeEach
    void setup() throws IOException {
        objectUnderTest = new QRToPaymentDecoderImpl();
        qrCodeFromFile = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(GENERIC_PAYABLE_QR_IMAGE_PATH)).getFile()));
    }

    @Test
    void decode_HappyCase() throws NotFoundException, InvalidPayableException {
        final UAQRPayable genericPayable = SimplePayableFixture.createGenericSimplePayable();
        final UAQRPayable decoded = objectUnderTest.decode(qrCodeFromFile);
        Assertions.assertThat(decoded).isEqualTo(genericPayable);
    }

    @Test
    void decode_QRCodeWithExtraField_ThrowsException() {
        assertThrows(InvalidPayableException.class, () -> {
            BufferedImage invalidImage = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(QR_IMAGE_WITH_EXTRA_FIELD_PATH)).getFile()));
            objectUnderTest.decode(invalidImage);
        });
    }

    @Test
    void decode_BarcodeNotFoundInImage_ThrowsException() {
        assertThrows(NotFoundException.class, () -> {
            BufferedImage invalidImage = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(NOT_QR_CODE_IMAGE_PATH)).getFile()));
            objectUnderTest.decode(invalidImage);
        });
    }

}