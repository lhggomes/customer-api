package com.ada.customer.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for PersonCustomer class")
public class PersonCustomerTest {

    @Test
    @DisplayName("Test toString() from Builder Class")
    public void testBuilderToStringMethod(){

        PersonCustomer.PersonCustomerBuilder customerBuilder = new PersonCustomer.PersonCustomerBuilder();
        assertInstanceOf(String.class, customerBuilder.toString());

    }

    @Test
    @DisplayName("Test class constructor")
    public void testClassConstructor(){

        PersonCustomer customer = new PersonCustomer();
        customer.setId(1L);

        assertInstanceOf(PersonCustomer.class, customer);

    }

}