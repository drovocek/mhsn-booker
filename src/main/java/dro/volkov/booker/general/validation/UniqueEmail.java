package dro.volkov.booker.general.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = EmailUniqueValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
@Documented
public @interface UniqueEmail {
    String message() default "Email must by unique";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}