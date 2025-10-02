package api.common.annotation;

import api.common.validator.LocalTimeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = LocalTimeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLocalTime {
    String message() default "Local time is invalid, this time does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

