package io.github.uaqrpayment.models;

import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_APPLICATION_START_CODE;
import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_BIC;
import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_DISPLAY_VALUE;
import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_ENCODING;
import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_FORMAT_VERSION;
import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_FUNCTION;
import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_REFERENCE;
import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_SERVICE_TAG;
import static io.github.uaqrpayment.api.UAQRPayable.DEFAULT_TARGET;

// Factory collection for pre-created payables.
public final class SimplePayableFixture {

    public static final String GENERIC_BENEFICIARY = "БО Благодійний фонд Сергія Притули";
    public static final String GENERIC_BENEFICIARY_ACC = "UA473052990000026005026707459";
    public static final String GENERIC_MONETARY_VALUE = "UAH100000.00";
    public static final String GENERIC_BENEFICIARY_TAX_NUMBER = "43720363";
    public static final String GENERIC_PAYMENT_PURPOSE = "На Байрактарчики!";

    public static SimplePayable createGenericSimplePayable() {
        return createGenericSimplePayableBuilder().createSimplePayable();
    }

    public static SimplePayable.SimplePayableBuilder createGenericSimplePayableBuilder() {
        return new SimplePayable.SimplePayableBuilder()
            .withApplicationStartCode(DEFAULT_APPLICATION_START_CODE)
            .withServiceTag(DEFAULT_SERVICE_TAG)
            .withFormatVersion(DEFAULT_FORMAT_VERSION)
            .withEncoding(DEFAULT_ENCODING)
            .withFunction(DEFAULT_FUNCTION)
            .withBIC(DEFAULT_BIC)
            .withBeneficiary(GENERIC_BENEFICIARY)
            .withBeneficiaryAccount(GENERIC_BENEFICIARY_ACC)
            .withMonetaryValue(GENERIC_MONETARY_VALUE)
            .withBeneficiaryTaxNumber(GENERIC_BENEFICIARY_TAX_NUMBER)
            .withTarget(DEFAULT_TARGET)
            .withReference(DEFAULT_REFERENCE)
            .withPaymentPurpose(GENERIC_PAYMENT_PURPOSE)
            .withDisplayValue(DEFAULT_DISPLAY_VALUE);
    }

    public static SimplePayable createGenericSimplePayableWithSuffix(final String suffix) {
        return new SimplePayable.SimplePayableBuilder()
            .withBeneficiary(GENERIC_BENEFICIARY + suffix)
            .withBeneficiaryAccount(GENERIC_BENEFICIARY_ACC + suffix)
            .withMonetaryValue(GENERIC_MONETARY_VALUE + suffix)
            .withBeneficiaryTaxNumber(GENERIC_BENEFICIARY_TAX_NUMBER + suffix)
            .withPaymentPurpose(GENERIC_PAYMENT_PURPOSE + suffix)
            .createSimplePayable();
    }
}
