package ru.renett.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SameFieldsValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SameFields {
    String message() default "Fields should be the same";

    String one();
    String two();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
