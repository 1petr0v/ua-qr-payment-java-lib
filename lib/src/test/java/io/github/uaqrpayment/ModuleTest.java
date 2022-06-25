package io.github.uaqrpayment;

import com.google.zxing.NotFoundException;
import io.github.uaqrpayment.api.PaymentToQREncoder;
import io.github.uaqrpayment.api.QRToPaymentDecoder;
import io.github.uaqrpayment.api.UAQRPayable;
import io.github.uaqrpayment.api.exception.InvalidPayableException;
import io.github.uaqrpayment.impl.PaymentToQREncoderImpl;
import io.github.uaqrpayment.impl.QRToPaymentDecoderImpl;
import io.github.uaqrpayment.models.SimplePayable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.awt.image.BufferedImage;

public class ModuleTest {
    private final PaymentToQREncoder encoder = new PaymentToQREncoderImpl();
    private final QRToPaymentDecoder decoder = new QRToPaymentDecoderImpl();

    @ParameterizedTest(name = "{index} => Testing payable with {0} : {1} : {2} : {3} : {4}")
    @CsvFileSource(resources = {"/nbu_provided_simplified_cases.csv"}, delimiter = ';')
    void doEncodingAndDecoding_SimplifiedDataSource_HappyCase(
            final String beneficiary,
            final String beneficiaryAccount,
            final String monetaryValue,
            final String beneficiaryTaxNumber,
            final String paymentPurpose
    ) throws InvalidPayableException, NotFoundException {
        // Create payable from CSV
        UAQRPayable loadedFromFile = new SimplePayable(beneficiary, beneficiaryAccount, monetaryValue, beneficiaryTaxNumber, paymentPurpose);
        // Generate QR code and keep it in memory
        BufferedImage image = encoder.encode(loadedFromFile);
        // Take previously generated QR code from memory and create payable from it
        UAQRPayable loadedFromQrCode = decoder.decode(image);
        // Verify both are equal
        Assertions.assertThat(loadedFromQrCode).isEqualTo(loadedFromFile);
    }
}
