package br.com.zup.ecommerce.shared.validation.annotation;

import br.com.zup.ecommerce.shared.validation.validator.UniqueValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {
    String message() default "{br.com.zup.ecommerce.unique}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String field();

    Class<?> domainClass();
}
