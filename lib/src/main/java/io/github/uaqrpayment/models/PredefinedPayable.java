package io.github.uaqrpayment.models;

import io.github.uaqrpayment.api.UAQRPayable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

// FIXME: probably orginize better
public class PredefinedPayable implements UAQRPayable {

    private final String applicationStartCode;
    private final String serviceTag;
    private final String formatVersion;
    private final String encoding;
    private final String function;
    private final String BIC;
    private final String beneficiary;
    private final String beneficiaryAccount;
    private final String monetaryValue;
    private final String beneficiaryTaxNumber;
    private final String target;
    private final String reference;
    private final String paymentPurpose;
    private final String displayValue;

    public PredefinedPayable(final String applicationStartCode,
                             final String serviceTag,
                             final String formatVersion,
                             final String encoding,
                             final String function,
                             final String BIC,
                             final String beneficiary,
                             final String beneficiaryAccount,
                             final String monetaryValue,
                             final String beneficiaryTaxNumber,
                             final String target,
                             final String reference,
                             final String paymentPurpose,
                             final String displayValue) {
        this.applicationStartCode = applicationStartCode;
        this.serviceTag = serviceTag;
        this.formatVersion = formatVersion;
        this.encoding = encoding;
        this.function = function;
        this.BIC = BIC;
        this.beneficiary = beneficiary;
        this.beneficiaryAccount = beneficiaryAccount;
        this.monetaryValue = monetaryValue;
        this.beneficiaryTaxNumber = beneficiaryTaxNumber;
        this.target = target;
        this.reference = reference;
        this.paymentPurpose = paymentPurpose;
        this.displayValue = displayValue;
    }

    public PredefinedPayable(final String beneficiary,
                             final String beneficiaryAccount,
                             final String monetaryValue,
                             final String beneficiaryTaxNumber,
                             final String paymentPurpose) {
        this(DEFAULT_APPLICATION_START_CODE,
            DEFAULT_SERVICE_TAG,
            DEFAULT_FORMAT_VERSION,
            DEFAULT_ENCODING,
            DEFAULT_FUNCTION,
            DEFAULT_BIC,
            beneficiary,
            beneficiaryAccount,
            monetaryValue,
            beneficiaryTaxNumber,
            DEFAULT_TARGET,
            DEFAULT_REFERENCE,
            paymentPurpose,
            DEFAULT_DISPLAY_VALUE);
    }

    @Override
    public String applicationStartCode() {
        return applicationStartCode;
    }

    @Override
    public String serviceTag() {
        return serviceTag;
    }

    @Override
    public String formatVersion() {
        return formatVersion;
    }

    @Override
    public String encoding() {
        return encoding;
    }

    @Override
    public String function() {
        return function;
    }

    @Override
    public String BIC() {
        return BIC;
    }

    @Override
    public String beneficiary() {
        return beneficiary;
    }

    @Override
    public String beneficiaryAccount() {
        return beneficiaryAccount;
    }

    @Override
    public String monetaryValue() {
        return monetaryValue;
    }

    @Override
    public String beneficiaryTaxNumber() {
        return beneficiaryTaxNumber;
    }

    @Override
    public String target() {
        return target;
    }

    @Override
    public String reference() {
        return reference;
    }

    @Override
    public String paymentPurpose() {
        return paymentPurpose;
    }

    @Override
    public String displayValue() {
        return displayValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        PredefinedPayable that = (PredefinedPayable) o;

        return new EqualsBuilder().append(applicationStartCode, that.applicationStartCode).append(serviceTag, that.serviceTag).append(formatVersion, that.formatVersion).append(encoding, that.encoding).append(function, that.function).append(BIC, that.BIC).append(beneficiary, that.beneficiary).append(beneficiaryAccount, that.beneficiaryAccount).append(monetaryValue, that.monetaryValue).append(beneficiaryTaxNumber, that.beneficiaryTaxNumber).append(target, that.target).append(reference, that.reference).append(paymentPurpose, that.paymentPurpose).append(displayValue, that.displayValue).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(applicationStartCode).append(serviceTag).append(formatVersion).append(encoding).append(function).append(BIC).append(beneficiary).append(beneficiaryAccount).append(monetaryValue).append(beneficiaryTaxNumber).append(target).append(reference).append(paymentPurpose).append(displayValue).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("applicationStartCode", applicationStartCode)
            .append("serviceTag", serviceTag)
            .append("formatVersion", formatVersion)
            .append("encoding", encoding)
            .append("function", function)
            .append("BIC", BIC)
            .append("beneficiary", beneficiary)
            .append("beneficiaryAccount", beneficiaryAccount)
            .append("monetaryValue", monetaryValue)
            .append("beneficiaryTaxNumber", beneficiaryTaxNumber)
            .append("target", target)
            .append("reference", reference)
            .append("paymentPurpose", paymentPurpose)
            .append("displayValue", displayValue)
            .toString();
    }
}
