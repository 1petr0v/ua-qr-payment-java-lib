package io.github.uaqrpayment.api;

import io.github.uaqrpayment.api.annotations.NBUQRFieldAttribute;

import static io.github.uaqrpayment.api.annotations.Encoding.ISO_646;
import static io.github.uaqrpayment.api.annotations.Encoding.UTF_8;
import static io.github.uaqrpayment.api.annotations.LengthType.FIXED;
import static io.github.uaqrpayment.api.annotations.LengthType.VARIABLE;
import static io.github.uaqrpayment.api.annotations.LengthUnit.BYTES;
import static io.github.uaqrpayment.api.annotations.LengthUnit.CHARS;
import static io.github.uaqrpayment.api.annotations.Requirement.MANDATORY;
import static io.github.uaqrpayment.api.annotations.Requirement.OPTIONAL;
import static io.github.uaqrpayment.api.annotations.Requirement.RESERVE;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.repeat;

public interface UAQRPayable {

    String FIELD_DELIMITER = "\r\n";

    String DEFAULT_APPLICATION_START_CODE = repeat(SPACE, 23);
    String DEFAULT_SERVICE_TAG = "BCD";
    String DEFAULT_FORMAT_VERSION = "001";
    String DEFAULT_ENCODING = "1";
    String DEFAULT_FUNCTION = "UCT";
    String DEFAULT_BIC = EMPTY;
    String DEFAULT_TARGET = EMPTY;
    String DEFAULT_REFERENCE = EMPTY;
    String DEFAULT_DISPLAY_VALUE = EMPTY;
    String DEFAULT_FIXED_FIELD_BACKFILL = SPACE;

    @NBUQRFieldAttribute(fieldNumber = 1, length = 23, lengthUnit = BYTES, lengthType = FIXED, requirement = MANDATORY, encoding = ISO_646)
    String applicationStartCode();

    @NBUQRFieldAttribute(fieldNumber = 2, length = 3, lengthUnit = BYTES, lengthType = FIXED, requirement = MANDATORY, encoding = ISO_646)
    String serviceTag();

    @NBUQRFieldAttribute(fieldNumber = 3, length = 3, lengthUnit = BYTES, lengthType = FIXED, requirement = MANDATORY, encoding = ISO_646)
    String formatVersion();

    @NBUQRFieldAttribute(fieldNumber = 4, length = 1, lengthUnit = BYTES, lengthType = FIXED, requirement = MANDATORY, encoding = ISO_646)
    String encoding();

    @NBUQRFieldAttribute(fieldNumber = 5, length = 3, lengthUnit = BYTES, lengthType = FIXED, requirement = MANDATORY, encoding = ISO_646)
    String function();

    @NBUQRFieldAttribute(fieldNumber = 6, length = 11, lengthUnit = BYTES, lengthType = VARIABLE, requirement = RESERVE, encoding = ISO_646)
    String BIC();

    @NBUQRFieldAttribute(fieldNumber = 7, length = 70, lengthUnit = CHARS, lengthType = VARIABLE, requirement = MANDATORY, encoding = UTF_8)
    String beneficiary();

    @NBUQRFieldAttribute(fieldNumber = 8, length = 34, lengthUnit = BYTES, lengthType = VARIABLE, requirement = MANDATORY, encoding = ISO_646)
    String beneficiaryAccount();

    @NBUQRFieldAttribute(fieldNumber = 9, length = 15, lengthUnit = BYTES, lengthType = VARIABLE, requirement = OPTIONAL, encoding = ISO_646)
    String monetaryValue();

    @NBUQRFieldAttribute(fieldNumber = 10, length = 10, lengthUnit = BYTES, lengthType = VARIABLE, requirement = MANDATORY, encoding = ISO_646)
    String beneficiaryTaxNumber();

    @NBUQRFieldAttribute(fieldNumber = 11, length = 4, lengthUnit = BYTES, lengthType = VARIABLE, requirement = RESERVE, encoding = ISO_646)
    String target();

    @NBUQRFieldAttribute(fieldNumber = 12, length = 35, lengthUnit = BYTES, lengthType = VARIABLE, requirement = RESERVE, encoding = ISO_646)
    String reference();

    @NBUQRFieldAttribute(fieldNumber = 13, length = 140, lengthUnit = CHARS, lengthType = VARIABLE, requirement = MANDATORY, encoding = UTF_8)
    String paymentPurpose();

    @NBUQRFieldAttribute(fieldNumber = 14, length = 70, lengthUnit = CHARS, lengthType = VARIABLE, requirement = RESERVE, encoding = UTF_8)
    String displayValue();
}
