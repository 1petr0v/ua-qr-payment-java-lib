package io.github.uaqrpayment.api.exception;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

// While this test may seem useless, please do not delete it.
// It may become handy in case someone decides to make deep refactoring of this library
// and adds some extra use-cases / validations / assertions into the exception constructor.
class InvalidPayableExceptionTest {

    @ParameterizedTest
    @ValueSource(strings = {"Message", "    ", ""})
    void createNewInstance_HappyCase(final String input) {
        Exception e = new InvalidPayableException(input);
        Assertions.assertThat(e).isNotNull();
    }

    // Apparently, null is not allowed to be passed into @ValueSource thus handling this test separately.
    @Test
    void createNewInstance_ArgumentNull_InstanceCreated() {
        Exception e = new InvalidPayableException(null);
        Assertions.assertThat(e).isNotNull();
    }

}