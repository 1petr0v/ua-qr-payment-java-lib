package io.github.uaqrpayment.models;

// Factory collection for pre-created payables.
public final class SimplePayableFixture {

    public static final String GENERIC_BENEFICIARY = "БО Благодійний фонд Сергія Притули";
    public static final String GENERIC_BENEFICIARY_ACC = "UA473052990000026005026707459";
    public static final String GENERIC_MONETARY_VALUE = "UAH100000.00";
    public static final String GENERIC_BENEFICIARY_TAX_NUMBER = "43720363";
    public static final String GENERIC_PAYMENT_PURPOSE = "На Байрактарчики!";

    public static SimplePayable createGenericSimplePayable() {
        return new SimplePayable(
            GENERIC_BENEFICIARY,
            GENERIC_BENEFICIARY_ACC,
            GENERIC_MONETARY_VALUE,
            GENERIC_BENEFICIARY_TAX_NUMBER,
            GENERIC_PAYMENT_PURPOSE
        );
    }

    public static SimplePayable createGenericSimplePayableWithSuffix(final String suffix) {
        return new SimplePayable(
            GENERIC_BENEFICIARY + suffix,
            GENERIC_BENEFICIARY_ACC + suffix,
            GENERIC_MONETARY_VALUE + suffix,
            GENERIC_BENEFICIARY_TAX_NUMBER + suffix,
            GENERIC_PAYMENT_PURPOSE + suffix
        );
    }
}
