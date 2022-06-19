package io.github.uaqrpayment.api.exception;

public class InvalidPayable extends Exception {

    public InvalidPayable(final String message) {
        super(message);
    }

    public InvalidPayable(final String message, final Throwable e) {
        super(message, e);
    }
}
