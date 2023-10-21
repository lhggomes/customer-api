package com.ada.customer.controller;

import com.ada.customer.dto.BusinessCustomerDto;
import com.ada.customer.dto.PersonCustomerDto;
import com.ada.customer.entity.PersonCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;
import com.ada.customer.services.PersonCustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test PersonCustomerController Class")
public class PersonCustomerControllerTest {

    @InjectMocks
    private PersonCustomerController customerController;
    @Mock
    private PersonCustomerServiceImpl customerService;

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

            Mockito.when(customerService.getCustomer(id)).thenReturn(Optional.of(PersonCustomer.builder()
                    .cpf("05214532585")
                    .mcc("2541")
                    .email("test@test.com.br")
                    .id(1L)
                    .build()));


            ResponseEntity response = customerController.getById(id);

            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertInstanceOf(PersonCustomerDto.class, response.getBody());

        }

        @ParameterizedTest
        @MethodSource("getClientId")
        @DisplayName("Test get by id invalid")
        public void testGetById_Invalid(Long id) {

            Mockito.when(customerService.getCustomer(id)).thenReturn(Optional.empty());
            ResponseEntity response = customerController.getById(id);

            assertTrue(response.getStatusCode().is4xxClientError());
            assertInstanceOf(Map.class, response.getBody());

        }

        public static Stream<Arguments> getClientId() {
            return Stream.of(Arguments.of(1L, Arguments.of(2L), Arguments.of(3L)));
        }
    }

    @Nested
    class DeleteById {

        @ParameterizedTest
        @MethodSource("getClientId")
        @DisplayName("Test delete by id with valid request")
        public void testDeleteById_Valid(Long id) {

            Mockito.doNothing().when(customerService).deleteCustomer(id);

            ResponseEntity<Void> response = customerController.deleteById(id);
            assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        }

        public static Stream<Arguments> getClientId() {
            return Stream.of(Arguments.of(1L, Arguments.of(2L), Arguments.of(3L)));
        }
    }

    @Nested
    class Update {

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Errors")
        @DisplayName("Test update customer with errors in request")
        public void testUpdate_RequestError(Long id, PersonCustomerDto personCustomerDto) {

            ResponseEntity<HashMap<String, String>> response = customerController.update(id, personCustomerDto);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertInstanceOf(Map.class, response.getBody());

        }

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Valid")
        @DisplayName("Test update customer with valid request")
        public void testUpdate_RequestValid(Long id, PersonCustomerDto personCustomerDto) throws ClientDoesNotExist {


            Mockito.doNothing().when(customerService).updateCustomer(id, Mockito.eq(Mockito.any()));
            ResponseEntity response = customerController.update(id, personCustomerDto);

            assertTrue(response.getStatusCode().is2xxSuccessful());

        }

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Valid")
        @DisplayName("Test update customer with raises")
        public void testUpdate_WithRaises(Long id, PersonCustomerDto personCustomerDto) throws ClientDoesNotExist {

            Mockito.doThrow(ClientDoesNotExist.class).when(customerService)
                    .updateCustomer(Mockito.any(), Mockito.any());


            ResponseEntity response = customerController.update(id, personCustomerDto);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertInstanceOf(Map.class, response.getBody());

        }

        public static Stream<Arguments> getBusinessCustomer_Valid() {
            return Stream.of(
                    Arguments.of(1L, PersonCustomerDto.builder()
                            .cpf("97500300018")
                            .mcc("2541")
                            .name("TEST")
                            .email("test@test.com.br")
                            .build())
            );
        }

        public static Stream<Arguments> getBusinessCustomer_Errors() {
            return Stream.of(
                    Arguments.of(1L, PersonCustomerDto.builder()
                            .cpf("05214532585")
                            .mcc("2541")
                            .name("TEST")
                            .email("test@test.com.br")
                            .build()),
                    Arguments.of(2L, PersonCustomerDto.builder()
                            .cpf("05214532583")
                            .mcc("2541")
                            .name("TEST")
                            .email("test@test.com.br")
                            .build()));
        }

    }

    @Nested
    class CreateCustomer {

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Valid")
        @DisplayName("Test create customer with valid request")
        public void testUpdate_RequestValid(PersonCustomerDto personCustomerDto) throws ClientAlreadyExists {

            Mockito.when(customerService.save(Mockito.any())).thenReturn(1L);
            ResponseEntity response = customerController.create(personCustomerDto);

            assertTrue(response.getStatusCode().is2xxSuccessful());
            assertInstanceOf(Long.class, response.getBody());

        }


        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Errors")
        @DisplayName("Test create using request with error")
        public void testCreate_RequestWithErrors(PersonCustomerDto personCustomerDto) {

            ResponseEntity<HashMap<String, String>> response = customerController.create(personCustomerDto);

            assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
            assertInstanceOf(Map.class, response.getBody());
        }

        @ParameterizedTest
        @MethodSource("getBusinessCustomer_Valid")
        @DisplayName("Test create customer with raises")
        public void testUpdate_WithRaises(PersonCustomerDto personCustomerDto) throws ClientAlreadyExists {

            Mockito.doThrow(ClientAlreadyExists.class).when(customerService)
                    .save(Mockito.any());


            ResponseEntity response = customerController.create(personCustomerDto);

            assertEquals(HttpStatus.BAD_REQUEST,  response.getStatusCode());
            assertInstanceOf(Map.class, response.getBody());

        }


        public static Stream<Arguments> getBusinessCustomer_Valid() {
            return Stream.of(
                    Arguments.of(PersonCustomerDto.builder()
                            .cpf("97500300018")
                            .mcc("2541")
                            .name("TEST")
                            .email("test@test.com.br")
                            .build())
            );
        }

        public static Stream<Arguments> getBusinessCustomer_Errors() {
            return Stream.of(
                    Arguments.of(PersonCustomerDto.builder()
                            .cpf("05214532585")
                            .mcc("2541")
                            .name("TEST")
                            .email("test@test.com.br")
                            .build()),
                    Arguments.of(PersonCustomerDto.builder()
                            .cpf("05214532583")
                            .mcc("2541")
                            .name("TEST")
                            .email("test@test.com.br")
                            .build()));
        }

    }


}