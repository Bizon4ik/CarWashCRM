package biz.podoliako.carwash.services.validation;

import biz.podoliako.carwash.services.validation.impl.CategoryNameIsUniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CategoryNameIsUniqueValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryNameUnique {
    String message() default ("Категория с таким именем уже существует");
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
