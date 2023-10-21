package com.ada.customer.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test BusinessCustomer Class")
public class BusinessCustomerTest {

    @Test
    @DisplayName("Test class NoArgs constructor")
    public void testClassConstructor(){

        BusinessCustomer customer = new BusinessCustomer();
        assertInstanceOf(BusinessCustomer.class, customer);
    }

    @Test
    @DisplayName("Test class Builder constructor")
    public void testClassBuilder(){

        BusinessCustomer customer = BusinessCustomer.builder().build();
        assertInstanceOf(BusinessCustomer.class, customer);
    }

}