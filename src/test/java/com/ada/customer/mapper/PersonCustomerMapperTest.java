package com.ada.customer.mapper;

import com.ada.customer.dto.PersonCustomerDto;
import com.ada.customer.entity.PersonCustomer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for PersonCustomerMapper")
public class PersonCustomerMapperTest {

    @Test
    @DisplayName("Test PersonCustomerMapper constructor")
    public void testToPersonCustomer_Constructor() {

        PersonCustomerMapper personCustomerMapper = new PersonCustomerMapper();
        assertInstanceOf(PersonCustomerMapper.class, personCustomerMapper);

    }

    @Test
    @DisplayName("Test toPersonCustomerDto with null")
    public void testToPersonCustomerDto_Null() {

        PersonCustomerDto personCustomerDto = PersonCustomerMapper.toPersonCustomerDto(null);
        assertInstanceOf(PersonCustomerDto.class, personCustomerDto);

    }

    @Test
    @DisplayName("Test toPersonCustomer with null")
    public void testToPersonCustomer_Null() {

        PersonCustomer personCustomer = PersonCustomerMapper.toPersonCustomer(null);
        assertInstanceOf(PersonCustomer.class, personCustomer);

    }
}