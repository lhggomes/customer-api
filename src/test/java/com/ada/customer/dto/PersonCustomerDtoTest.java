package com.ada.customer.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test PersonCustomerDto class")
public class PersonCustomerDtoTest {


    @Test
    @DisplayName("Test PersonCustomerDtoBuilder method")
    public void testBuilderToString(){

        PersonCustomerDto.PersonCustomerDtoBuilder personCustomerDtoBuilder = new PersonCustomerDto.PersonCustomerDtoBuilder();
        assertInstanceOf(String.class, personCustomerDtoBuilder.toString());
    }


    @Test
    @DisplayName("Test Equals method")
    public void testPersonCustomerDtoEquals(){

        PersonCustomerDto personCustomerDtoA = new PersonCustomerDto();
        PersonCustomerDto personCustomerDtoB = new PersonCustomerDto();

        assertTrue(personCustomerDtoA.equals(personCustomerDtoB));
    }

    @Test
    @DisplayName("Test Hashcode method")
    public void testPersonCustomerDtoHashcode(){

        PersonCustomerDto personCustomerDtoA = new PersonCustomerDto();
        PersonCustomerDto personCustomerDtoB = new PersonCustomerDto();

        assertEquals(personCustomerDtoA.hashCode(), personCustomerDtoB.hashCode());
    }

    @Test
    @DisplayName("Test setters")
    public void testPersonCustomerDto_Setter(){

        PersonCustomerDto personCustomerDto = new PersonCustomerDto();
        personCustomerDto.setCpf("63224247002");
        personCustomerDto.setMcc("1234");
        personCustomerDto.setName("TEST");
        personCustomerDto.setEmail("test@test.com.br");


        assertEquals("63224247002", personCustomerDto.getCpf());
        assertEquals("1234", personCustomerDto.getMcc());
        assertEquals("TEST", personCustomerDto.getName());
        assertEquals("test@test.com.br", personCustomerDto.getEmail());

    }

}