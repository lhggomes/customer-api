package com.ada.customer.validators;

import com.ada.customer.dto.BusinessCustomerDto;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.HashMap;
import java.util.Set;

public class BusinessValidator {

    public static HashMap<String, String> validateBusinessCustomerDto(BusinessCustomerDto businessCustomer){

        Validator validator = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()
                .getValidator();

        Set<ConstraintViolation<BusinessCustomerDto>> violations = validator.validate(businessCustomer);

        if(!violations.isEmpty()){

            violations.forEach(violation -> {
                System.out.println(violation);
            });

        }

    }

}
