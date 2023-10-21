package com.ada.customer.validators;

import com.ada.customer.dto.BusinessCustomerDto;
import com.ada.customer.dto.PersonCustomerDto;
import lombok.NoArgsConstructor;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Set;

@NoArgsConstructor
public class BusinessRuleFieldsValidator {

    public static <T> HashMap<String, String> validateBusinessCustomerDto(T customer) {

        HashMap<String, String> errors = new HashMap<>();
        Validator validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();

        if (customer instanceof BusinessCustomerDto) {
            BusinessCustomerDto businessCustomer = (BusinessCustomerDto) customer;
            Set<ConstraintViolation<BusinessCustomerDto>> violations = validator.validate(businessCustomer);
            if (!violations.isEmpty()) {
                violations.forEach(violation -> {
                    errors.put(violation.getPropertyPath().toString(), violation.getMessage());

                });
            }
        } else if (customer instanceof PersonCustomerDto) {
            PersonCustomerDto personCustomer = (PersonCustomerDto) customer;
            Set<ConstraintViolation<PersonCustomerDto>> violations = validator.validate(personCustomer);
            if (!violations.isEmpty()) {
                violations.forEach(violation -> {
                    errors.put(violation.getPropertyPath().toString(), violation.getMessage());

                });
            }
        }
        return errors;
    }
}


