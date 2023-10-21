package com.ada.customer.controller;

import com.ada.customer.dto.BusinessCustomerDto;
import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;
import com.ada.customer.services.BusinessCustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test for class BusinessCustomer Controller")
public class BusinessCustomerControllerTest {

    @InjectMocks
    private BusinessCustomerController businessCustomerController;
    @Mock
    private BusinessCustomerServiceImpl businessCustomerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Nested
    class GetById {
        @ParameterizedTest
        @MethodSource("getClientId")
        @DisplayName("Test get by id valid")
        public void testGetById_Valid(Long id) {

            Mockito.when(businessCustomerService.getCustomer(id)).thenReturn(Optional.of(BusinessCustomer.builder()
                            .id(id)
                            .mcc("1425")
                            .razaoSocial("TEST")
                            .businessContactCpf("02072568541")
                            .businessEmailName("test@test.com.br")
                            .businessContactName("TEST")
                    .build()));


            ResponseEntity response = businessCustomerController.getById(id);

            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertInstanceOf(BusinessCustomerDto.class, response.getBody());

        }

        @ParameterizedTest
        @MethodSource("getClientId")
        @DisplayName("Test get by id invalid")
        public void testGetById_Invalid(Long id) {

            Mockito.when(businessCustomerService.getCustomer(id)).thenReturn(Optional.empty());
            ResponseEntity response = businessCustomerController.getById(id);

            assertTrue(response.getStatusCode().is4xxClientError());
            assertInstanceOf(Map.class, response.getBody());

        }

        public static Stream<Arguments> getClientId(){
            return Stream.of(Arguments.of(1L, Arguments.of(2L), Arguments.of(3L)));
        }
    }


    @Nested
    class DeleteById {

        @ParameterizedTest
        @MethodSource("getClientId")
        @DisplayName("Test delete by id with valid request")
        public void testDeleteById_Valid(Long id) {

            Mockito.doNothing().when(businessCustomerService).deleteCustomer(id);

            ResponseEntity<Void> response = businessCustomerController.deleteById(id);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        }

        public static Stream<Arguments> getClientId(){
            return Stream.of(Arguments.of(1L, Arguments.of(2L), Arguments.of(3L)));
        }


    }


    @Nested
    class Update {

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Errors")
        @DisplayName("Test update customer with errors in request")
        public void testUpdate_RequestError(Long id, BusinessCustomerDto businessCustomerDto) {

            ResponseEntity<HashMap<String, String>> response = businessCustomerController.update(id, businessCustomerDto);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertInstanceOf(Map.class, response.getBody());

        }

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Valid")
        @DisplayName("Test update customer with valid request")
        public void testUpdate_RequestValid(Long id, BusinessCustomerDto businessCustomerDto) throws ClientDoesNotExist {


            Mockito.doNothing().when(businessCustomerService).updateCustomer(id, Mockito.eq(Mockito.any()));
            ResponseEntity response = businessCustomerController.update(id, businessCustomerDto);

            assertTrue(response.getStatusCode().is2xxSuccessful());

        }

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Valid")
        @DisplayName("Test update customer with raises")
        public void testUpdate_WithRaises(Long id, BusinessCustomerDto businessCustomerDto) throws ClientDoesNotExist {

            Mockito.doThrow(ClientDoesNotExist.class).when(businessCustomerService)
                    .updateCustomer(Mockito.any(), Mockito.any());


            ResponseEntity response = businessCustomerController.update(id, businessCustomerDto);

            assertEquals(HttpStatus.BAD_REQUEST,  response.getStatusCode());
            assertInstanceOf(Map.class, response.getBody());

        }

        public static Stream<Arguments> getBusinessCustomer_Valid() {
            return Stream.of(
                    Arguments.of(1L, BusinessCustomerDto.builder()
                            .businessContactName("TEST")
                            .businessContactCpf("67901935022")
                            .cnpj("49507674000176")
                            .businessEmailName("test@test.com.br")
                            .razaoSocial("TEST BUSINESS")
                            .mcc("1425")
                            .build(), Arguments.of(1L, BusinessCustomerDto.builder()
                            .businessContactName("TEST")
                            .businessContactCpf("04512347070")
                            .cnpj("65969001000194")
                            .businessEmailName("test@test.com.br")
                            .razaoSocial("TEST BUSINESS")
                            .mcc("1425")
                            .build()))
            );
        }

        public static Stream<Arguments> getBusinessCustomer_Errors() {
            return Stream.of(
                    Arguments.of(1L, BusinessCustomerDto.builder()
                            .businessContactName("TEST")
                            .businessContactCpf("33073975080")
                            .cnpj("67438898000155")
                            .businessEmailName("test@test.com.br")
                            .razaoSocial("")
                            .mcc("1425")
                            .build()),
                    Arguments.of(2L, BusinessCustomerDto.builder()
                            .businessContactName("TEST_02")
                            .businessContactCpf("92248018037")
                            .cnpj("62826430000114")
                            .businessEmailName("test2test.com.br")
                            .razaoSocial("TEST_02")
                            .mcc("1427")
                            .build()));
        }

    }


    @Nested
    class CreateCustomer {

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Valid")
        @DisplayName("Test create customer with valid request")
        public void testUpdate_RequestValid(BusinessCustomerDto businessCustomerDto) throws ClientAlreadyExists {

            Mockito.when(businessCustomerService.save(Mockito.any())).thenReturn(1L);
            ResponseEntity response = businessCustomerController.create(businessCustomerDto);

            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertInstanceOf(Long.class, response.getBody());

        }


        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Errors")
        @DisplayName("Test create using request with error")
        public void testCreate_RequestWithErrors(BusinessCustomerDto businessCustomerDto) {

            ResponseEntity<HashMap<String, String>> response = businessCustomerController.create(businessCustomerDto);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertInstanceOf(Map.class, response.getBody());
        }

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Valid")
        @DisplayName("Test create customer with raises")
        public void testUpdate_WithRaises(BusinessCustomerDto businessCustomerDto) throws ClientAlreadyExists {

            Mockito.doThrow(ClientAlreadyExists.class).when(businessCustomerService)
                    .save(Mockito.any());


            ResponseEntity response = businessCustomerController.create(businessCustomerDto);

            assertEquals(HttpStatus.BAD_REQUEST,  response.getStatusCode());
            assertInstanceOf(Map.class, response.getBody());

        }


        public static Stream<Arguments> getBusinessCustomer_Valid() {
            return Stream.of(
                    Arguments.of(BusinessCustomerDto.builder()
                            .businessContactName("TEST")
                            .businessContactCpf("67901935022")
                            .cnpj("49507674000176")
                            .businessEmailName("test@test.com.br")
                            .razaoSocial("TEST BUSINESS")
                            .mcc("1425")
                            .build(), Arguments.of(1L, BusinessCustomerDto.builder()
                            .businessContactName("TEST")
                            .businessContactCpf("04512347070")
                            .cnpj("65969001000194")
                            .businessEmailName("test@test.com.br")
                            .razaoSocial("TEST BUSINESS")
                            .mcc("1425")
                            .build()))
            );
        }

        public static Stream<Arguments> getBusinessCustomer_Errors() {
            return Stream.of(
                    Arguments.of(BusinessCustomerDto.builder()
                            .businessContactName("TEST")
                            .businessContactCpf("33073975080")
                            .cnpj("67438898000155")
                            .businessEmailName("test@test.com.br")
                            .razaoSocial("")
                            .mcc("1425")
                            .build()),
                    Arguments.of(BusinessCustomerDto.builder()
                            .businessContactName("TEST_02")
                            .businessContactCpf("92248018037")
                            .cnpj("62826430000114")
                            .businessEmailName("test2test.com.br")
                            .razaoSocial("TEST_02")
                            .mcc("1427")
                            .build()));
        }

    }


}