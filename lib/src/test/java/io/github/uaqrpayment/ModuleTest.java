package io.github.uaqrpayment;

import io.github.uaqrpayment.api.PaymentToQREncoder;
import io.github.uaqrpayment.api.UAQRPayable;
import io.github.uaqrpayment.api.exception.InvalidPayable;
import io.github.uaqrpayment.impl.PaymentToQREncoderImpl;
import io.github.uaqrpayment.models.PredefinedPayable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ModuleTest {
    private PaymentToQREncoder encoder = new PaymentToQREncoderImpl();

    @ParameterizedTest(name = "{index} => Testing payable with {0} : {1} : {2} : {3} : {4}")
    @CsvFileSource(resources = {"/nbu_provided_simplified_cases.csv"}, encoding = "UTF-8", delimiter = ';')
    void doEncodingAndDecoding_SimplifiedDataSource_HappyCase(
            final String beneficiary,
            final String beneficiaryAccount,
            final String monetaryValue,
            final String beneficiaryTaxNumber,
            final String paymentPurpose
    ) throws IOException, InvalidPayable {
        UAQRPayable loadedFromFile =
            new PredefinedPayable(beneficiary, beneficiaryAccount, monetaryValue, beneficiaryTaxNumber, paymentPurpose);
        BufferedImage image = encoder.encode(loadedFromFile);
        System.out.println(loadedFromFile);
        File tmpFile = File.createTempFile("ua-qr-payment-java-lib", ".png");
        System.out.println(tmpFile.getAbsolutePath());
        ImageIO.write(image, "png", tmpFile);
    }
}
