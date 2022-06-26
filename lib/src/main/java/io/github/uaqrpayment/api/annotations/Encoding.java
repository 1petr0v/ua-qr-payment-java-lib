package io.github.uaqrpayment.api.annotations;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * 
 */
public enum Encoding {
    ISO_646(StandardCharsets.US_ASCII), UTF_8(StandardCharsets.UTF_8);

    private final Charset charset;

    Encoding(final Charset charset) {
        this.charset = charset;
    }

    public Charset charset() {
        return charset;
    }
}
