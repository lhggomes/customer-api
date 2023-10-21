package com.ada.customer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Tests for BusinessCustomerDto class")
public class BusinessCustomerDtoTest {

    @Test
    @DisplayName("Test BusinessCustomerDto.Builder method")
    public void testBuilderToString(){

        BusinessCustomerDto.BusinessCustomerDtoBuilder businessCustomerDtoA = new BusinessCustomerDto.BusinessCustomerDtoBuilder();
        assertInstanceOf(String.class, businessCustomerDtoA.toString());
    }

    @Test
    @DisplayName("Test Hashcode method")
    public void testHashcode_Equals(){

        BusinessCustomerDto businessCustomerDtoA = new BusinessCustomerDto();
        BusinessCustomerDto businessCustomerDtoB = new BusinessCustomerDto();

        assertEquals(businessCustomerDtoA.hashCode(), businessCustomerDtoB.hashCode());
    }

    @Test
    @DisplayName("Test Equals method")
    public void testEquals(){

        BusinessCustomerDto businessCustomerDtoA = new BusinessCustomerDto();
        BusinessCustomerDto businessCustomerDtoB = new BusinessCustomerDto();

        assertTrue(businessCustomerDtoA.equals(businessCustomerDtoB));
    }


    @Test
    @DisplayName("Test setters from class")
    public void testSetters(){

        BusinessCustomerDto businessCustomerDto = new BusinessCustomerDto();
        businessCustomerDto.setMcc("1423");
        businessCustomerDto.setCnpj("05412654122216");
        businessCustomerDto.setBusinessContactName("TEST");
        businessCustomerDto.setBusinessContactCpf("02041565245");
        businessCustomerDto.setRazaoSocial("TEST");
        businessCustomerDto.setBusinessEmailName("test@test.com.br");


        assertEquals("1423", businessCustomerDto.getMcc());
        assertEquals("05412654122216", businessCustomerDto.getCnpj());
        assertEquals("TEST", businessCustomerDto.getBusinessContactName());
        assertEquals("02041565245", businessCustomerDto.getBusinessContactCpf());
        assertEquals("TEST", businessCustomerDto.getRazaoSocial());
        assertEquals("test@test.com.br", businessCustomerDto.getBusinessEmailName());


    }

}