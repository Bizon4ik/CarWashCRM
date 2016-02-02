package biz.podoliako.carwash.services.validation;


import biz.podoliako.carwash.services.validation.impl.ClientNameIsUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ClientNameIsUniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientNameUnique {
    String message() default ("Имя клиента уже существует");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
