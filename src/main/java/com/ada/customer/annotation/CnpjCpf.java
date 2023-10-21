package com.ada.customer.annotation;

import com.ada.customer.validators.CnpjCpfValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = {CnpjCpfValidator.class})
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface CnpjCpf {

    String message() default "CNPJ/CPF is invalid";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
