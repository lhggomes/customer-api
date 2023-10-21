package com.ada.customer.mapper;

import com.ada.customer.dto.BusinessCustomerDto;
import com.ada.customer.entity.BusinessCustomer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for BusinessCustomerMapper")
public class BusinessCustomerMapperTest {

    @Test
    @DisplayName("Test BusinessCustomerMapper constructor")
    public void testBusinessCustomerMapper_Constructor() {

        BusinessCustomerMapper businessCustomerMapper  = new BusinessCustomerMapper();
        assertInstanceOf(BusinessCustomerMapper.class, businessCustomerMapper);

    }

    @Test
    @DisplayName("Test toBusinessCustomer with null")
    public void testToBusinessCustomer_Null() {

        BusinessCustomer businessCustomer = BusinessCustomerMapper.toBusinessCustomer(null);
        assertInstanceOf(BusinessCustomer.class, businessCustomer);

    }

    @Test
    @DisplayName("Test toBusinessCustomerDto with null")
    public void testToBusinessCustomerDto_Null() {

        BusinessCustomerDto businessCustomerDto = BusinessCustomerMapper.toBusinessCustomerDto(null);
        assertInstanceOf(BusinessCustomerDto.class, businessCustomerDto);
    }
}