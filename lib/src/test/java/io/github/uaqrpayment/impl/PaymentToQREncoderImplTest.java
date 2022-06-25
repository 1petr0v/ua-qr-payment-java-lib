package io.github.uaqrpayment.impl;

import com.github.romankh3.image.comparison.ImageComparison;
import com.github.romankh3.image.comparison.model.ImageComparisonResult;
import com.github.romankh3.image.comparison.model.ImageComparisonState;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import io.github.uaqrpayment.api.UAQRPayable;
import io.github.uaqrpayment.api.exception.InvalidPayableException;
import io.github.uaqrpayment.models.SimplePayableFixture;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.anyString;

class PaymentToQREncoderImplTest {

    private static final String GENERIC_PAYABLE_QR_IMAGE_PATH = "/generic.png";

    private PaymentToQREncoderImpl objectUnderTest;
    private UAQRPayable genericPayable;

    @BeforeEach
    void setup() {
        objectUnderTest = new PaymentToQREncoderImpl();
        genericPayable = SimplePayableFixture.createGenericSimplePayable();
    }

    @Test
    void encode_HappyCase() throws InvalidPayableException, IOException {
        final BufferedImage qrCode = objectUnderTest.encode(genericPayable);
        final BufferedImage fromFile = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(GENERIC_PAYABLE_QR_IMAGE_PATH)).getFile()));
        ImageComparisonResult imageComparisonResult = new ImageComparison(qrCode, fromFile).compareImages();
        Assertions.assertThat(imageComparisonResult.getImageComparisonState()).isEqualTo(ImageComparisonState.MATCH);
    }

    @Test
    void encode_OutputStreamThrowsException_ExceptionThrown() {
        try (MockedConstruction<ByteArrayOutputStream> ignored =
                 Mockito.mockConstruction(ByteArrayOutputStream.class,
                     (mock, context) -> Mockito.doThrow(new IOException())
                         .when(mock).write(any(byte[].class)))) {
            assertThrows(RuntimeException.class, () -> objectUnderTest.encode(genericPayable));
        }
    }

    @Test
    void encode_MultiFormatWriterThrowsException_ExceptionThrown() {
        try (MockedConstruction<MultiFormatWriter> ignored =
                 Mockito.mockConstruction(MultiFormatWriter.class,
                     (mock, context) -> Mockito.doThrow(new WriterException())
                         .when(mock).encode(anyString(), any(BarcodeFormat.class), anyInt(), anyInt(), anyMap()))) {
            assertThrows(RuntimeException.class, () -> objectUnderTest.encode(genericPayable));
        }
    }

    @Test
    void encode_MandatoryPayableFieldWasEmpty_ThrowsException() {
        final UAQRPayable corruptedPayable = SimplePayableFixture.createGenericSimplePayableBuilder()
            .withEncoding(StringUtils.EMPTY) // MANDATORY
            .createSimplePayable();
        assertThrows(InvalidPayableException.class, () -> objectUnderTest.encode(corruptedPayable));
    }

    @Test
    void encode_ReservePayableFieldWasNotEmpty_ThrowsException() {
        final UAQRPayable corruptedPayable = SimplePayableFixture.createGenericSimplePayableBuilder()
            .withDisplayValue("MUST BE EMPTY") // RESERVED
            .createSimplePayable();
        assertThrows(InvalidPayableException.class, () -> objectUnderTest.encode(corruptedPayable));
    }

    @Test
    void encode_BytePayableFieldOverflow_ThrowsException() {
        final UAQRPayable corruptedPayable = SimplePayableFixture.createGenericSimplePayableBuilder()
            . withFunction("OVERFLOW") // Must be exactly 3 bytes (or 3 chars in 'ASCII' chars)
            .createSimplePayable();
        assertThrows(InvalidPayableException.class, () -> objectUnderTest.encode(corruptedPayable));
    }

    @Test
    void encode_CharsPayableFieldOverflow_ThrowsException() {
        final UAQRPayable corruptedPayable = SimplePayableFixture.createGenericSimplePayableBuilder()
            . withBeneficiary(StringUtils.repeat('A', 71)) // Must not exceed 70 chars
            .createSimplePayable();
        assertThrows(InvalidPayableException.class, () -> objectUnderTest.encode(corruptedPayable));
    }

    // Somewhat self-healing of FIXED fields
    @Test
    void encode_FixedBytesPayableFieldUnderflow_ThrowsException() throws InvalidPayableException, IOException {
        final UAQRPayable corruptedPayable = SimplePayableFixture.createGenericSimplePayableBuilder()
            .withApplicationStartCode("     ") // Must be exactly 23 bytes (or 3 chars in 'ASCII' chars), default 23 spaces
            .createSimplePayable();
        final BufferedImage qrCode = objectUnderTest.encode(corruptedPayable);
        final BufferedImage fromFile = ImageIO.read(new File(Objects.requireNonNull(this.getClass().getResource(GENERIC_PAYABLE_QR_IMAGE_PATH)).getFile()));
        ImageComparisonResult imageComparisonResult = new ImageComparison(qrCode, fromFile).compareImages();
        Assertions.assertThat(imageComparisonResult.getImageComparisonState()).isEqualTo(ImageComparisonState.MATCH);
    }

    @Test
    void encode_FixedCharsPayableFieldUnderflow_ThrowsException() {
        // Use-case is not currently supported,
        // as there is no current use case with FIXED+CHARS field.
    }

}