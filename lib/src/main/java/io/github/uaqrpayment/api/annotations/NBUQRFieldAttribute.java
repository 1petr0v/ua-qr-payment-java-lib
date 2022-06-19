package io.github.uaqrpayment.api.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 */
@Target({ ElementType.ANNOTATION_TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NBUQRFieldAttribute {

    int fieldNumber();
    int length();
    LengthUnit lengthUnit();
    LengthType lengthType();
    Requirement requirement();
    Encoding encoding();
}
