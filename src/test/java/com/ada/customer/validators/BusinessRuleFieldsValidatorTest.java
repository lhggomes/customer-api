package com.ada.customer.validators;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for BusinessRuleFieldsValidator class")
public class BusinessRuleFieldsValidatorTest {

    @Test
    @DisplayName("Test default class constructor")
    public void testClassDefaultConstructor(){

        BusinessRuleFieldsValidator businessRuleFieldsValidator = new BusinessRuleFieldsValidator();
        assertInstanceOf(BusinessRuleFieldsValidator.class, businessRuleFieldsValidator);

    }

}