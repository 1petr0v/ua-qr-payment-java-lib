package io.github.uaqrpayment.models;

import io.github.uaqrpayment.api.UAQRPayable;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@AllArgsConstructor
public class SimplePayable implements UAQRPayable {

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

    public SimplePayable(final String beneficiary,
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

    public static class SimplePayableBuilder {
        private String beneficiary;
        private String beneficiaryAccount;
        private String monetaryValue;
        private String beneficiaryTaxNumber;
        private String paymentPurpose;
        private String applicationStartCode = DEFAULT_APPLICATION_START_CODE;
        private String serviceTag = DEFAULT_SERVICE_TAG;
        private String formatVersion = DEFAULT_FORMAT_VERSION;
        private String encoding = DEFAULT_ENCODING;
        private String function = DEFAULT_FUNCTION;
        private String bic = DEFAULT_BIC;
        private String target = DEFAULT_TARGET;
        private String reference = DEFAULT_REFERENCE;
        private String displayValue = DEFAULT_DISPLAY_VALUE;

        public SimplePayableBuilder withBeneficiary(String beneficiary) {
            this.beneficiary = beneficiary;
            return this;
        }

        public SimplePayableBuilder withBeneficiaryAccount(String beneficiaryAccount) {
            this.beneficiaryAccount = beneficiaryAccount;
            return this;
        }

        public SimplePayableBuilder withMonetaryValue(String monetaryValue) {
            this.monetaryValue = monetaryValue;
            return this;
        }

        public SimplePayableBuilder withBeneficiaryTaxNumber(String beneficiaryTaxNumber) {
            this.beneficiaryTaxNumber = beneficiaryTaxNumber;
            return this;
        }

        public SimplePayableBuilder withPaymentPurpose(String paymentPurpose) {
            this.paymentPurpose = paymentPurpose;
            return this;
        }

        public SimplePayableBuilder withApplicationStartCode(String applicationStartCode) {
            this.applicationStartCode = applicationStartCode;
            return this;
        }

        public SimplePayableBuilder withServiceTag(String serviceTag) {
            this.serviceTag = serviceTag;
            return this;
        }

        public SimplePayableBuilder withFormatVersion(String formatVersion) {
            this.formatVersion = formatVersion;
            return this;
        }

        public SimplePayableBuilder withEncoding(String encoding) {
            this.encoding = encoding;
            return this;
        }

        public SimplePayableBuilder withFunction(String function) {
            this.function = function;
            return this;
        }

        public SimplePayableBuilder withBIC(String bic) {
            this.bic = bic;
            return this;
        }

        public SimplePayableBuilder withTarget(String target) {
            this.target = target;
            return this;
        }

        public SimplePayableBuilder withReference(String reference) {
            this.reference = reference;
            return this;
        }

        public SimplePayableBuilder withDisplayValue(String displayValue) {
            this.displayValue = displayValue;
            return this;
        }

        public SimplePayable createSimplePayable() {
            return new SimplePayable(
                applicationStartCode,
                serviceTag,
                formatVersion,
                encoding,
                function,
                bic,
                beneficiary,
                beneficiaryAccount,
                monetaryValue,
                beneficiaryTaxNumber,
                target,
                reference,
                paymentPurpose,
                displayValue
            );
        }
    }
    // Auto-generated by IntelliJ
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimplePayable that = (SimplePayable) o;
        return new EqualsBuilder()
            .append(applicationStartCode, that.applicationStartCode)
            .append(serviceTag, that.serviceTag)
            .append(formatVersion, that.formatVersion)
            .append(encoding, that.encoding)
            .append(function, that.function)
            .append(BIC, that.BIC)
            .append(beneficiary, that.beneficiary)
            .append(beneficiaryAccount, that.beneficiaryAccount)
            .append(monetaryValue, that.monetaryValue)
            .append(beneficiaryTaxNumber, that.beneficiaryTaxNumber)
            .append(target, that.target)
            .append(reference, that.reference)
            .append(paymentPurpose, that.paymentPurpose)
            .append(displayValue, that.displayValue)
            .isEquals();
    }

    // Auto-generated by IntelliJ
    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(applicationStartCode)
            .append(serviceTag)
            .append(formatVersion)
            .append(encoding)
            .append(function)
            .append(BIC)
            .append(beneficiary)
            .append(beneficiaryAccount)
            .append(monetaryValue)
            .append(beneficiaryTaxNumber)
            .append(target)
            .append(reference)
            .append(paymentPurpose)
            .append(displayValue)
            .toHashCode();
    }

    // WARNING! Dumping values as-is may be considered as a GDPR violation therefore may have severe legal consequences.
    // Hiding some values.
    // TODO: requires revisiting
    // TODO. Option/Feature 1. Do not repeat '*'. In theory, somebody can reverse engineer PII by beneficiary name length.
    // TODO. Option/Feature 2. Since toString is used for debugging, some values could be exposed. For example, first 2-3 letters of the beneficiary.
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
            .append("applicationStartCode", applicationStartCode)
            .append("serviceTag", serviceTag)
            .append("formatVersion", formatVersion)
            .append("encoding", encoding)
            .append("function", function)
            .append("BIC", StringUtils.repeat('*', BIC.length()))
            .append("beneficiary", StringUtils.repeat('*', beneficiary.length()))
            .append("beneficiaryAccount", StringUtils.repeat('*', beneficiaryAccount.length()))
            .append("monetaryValue", StringUtils.repeat('*', monetaryValue.length()))
            .append("beneficiaryTaxNumber", StringUtils.repeat('*', beneficiaryTaxNumber.length()))
            .append("target", target)
            .append("reference", reference)
            .append("paymentPurpose", StringUtils.repeat('*', paymentPurpose.length()))
            .append("displayValue", StringUtils.repeat('*', displayValue.length()))
            .toString();
    }
}
