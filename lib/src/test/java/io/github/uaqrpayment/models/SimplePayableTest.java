package io.github.uaqrpayment.models;

import org.junit.jupiter.api.Test;

import static io.github.uaqrpayment.models.SimplePayableFixture.createGenericSimplePayable;
import static io.github.uaqrpayment.models.SimplePayableFixture.createGenericSimplePayableWithSuffix;
import static org.assertj.core.api.Assertions.assertThat;

class SimplePayableTest {

    private static final String EXPECTED_TO_STRING_VALUE = "SimplePayable[applicationStartCode=                       ,serviceTag=BCD,formatVersion=001,encoding=1,function=UCT,BIC=,beneficiary=**********************************,beneficiaryAccount=*****************************,monetaryValue=*********,beneficiaryTaxNumber=********,target=,reference=,paymentPurpose=*****************,displayValue=]";

    @Test
    void instance_ToString_ExpectedOutput() {
        assertThat(createGenericSimplePayable().toString()).isEqualTo(EXPECTED_TO_STRING_VALUE);
    }

    @Test
    void instance_EqualsAndHashCode_ContractMatches() {
        assertThat(createGenericSimplePayableWithSuffix("1")).isEqualTo(createGenericSimplePayableWithSuffix("1"));
        assertThat(createGenericSimplePayableWithSuffix("2").hashCode()).isEqualTo(createGenericSimplePayableWithSuffix("2").hashCode());

        assertThat(createGenericSimplePayableWithSuffix("3")).isNotEqualTo(createGenericSimplePayableWithSuffix("4"));
        assertThat(createGenericSimplePayableWithSuffix("5").hashCode()).isNotEqualTo(createGenericSimplePayableWithSuffix("6").hashCode());
    }

}