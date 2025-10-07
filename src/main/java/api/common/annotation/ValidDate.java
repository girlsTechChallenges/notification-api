package api.common.annotation;

import api.common.validator.DateValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDate {
    String message() default "Date is invalid, this date does not exist";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
