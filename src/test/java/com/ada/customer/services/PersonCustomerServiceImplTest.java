package com.ada.customer.services;

import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.entity.PersonCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;
import com.ada.customer.repository.PersonCustomerRepository;
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

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Test PersonCustomerService class")
public class PersonCustomerServiceImplTest {

    @InjectMocks
    private PersonCustomerServiceImpl customerService;

    @Mock
    private PersonCustomerRepository customerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Nested
    class GetCustomer {

        @ParameterizedTest
        @DisplayName("Test get customer with a valid return")
        @MethodSource("getCustomerId")
        public void testGetCustomer_Valid(Long id) {

            Mockito.when(customerRepository.findById(id)).thenReturn(Optional.of(PersonCustomer.builder()
                            .name("TEST")
                            .mcc("1421")
                            .email("test@test.com.br")
                            .id(1L)
                            .cpf("02072465120")
                    .build()));


            Optional<PersonCustomer> businessCustomer = customerService.getCustomer(id);

            assertTrue(businessCustomer.isPresent());
            assertEquals("1421", businessCustomer.get().getMcc());

        }

        public static Stream<Arguments> getCustomerId() {
            return Stream.of(Arguments.of(1L), Arguments.of(2L), Arguments.of(3L));
        }

    }


    @Nested
    class DeleteCustomer {

        @ParameterizedTest
        @DisplayName("Test delete customer with a valid response")
        @MethodSource("getCustomerId")
        public void testDeleteCustomer_Valid(Long id) {

            Mockito.doNothing().when(customerRepository).deleteById(id);
            customerService.deleteCustomer(id);

            Mockito.verify(customerRepository).deleteById(id);
        }

        public static Stream<Arguments> getCustomerId() {
            return Stream.of(Arguments.of(1L), Arguments.of(2L), Arguments.of(3L));
        }

    }

    @Nested
    class SaveCustomer {

        @ParameterizedTest
        @MethodSource("getBusinessCustomerRequest")
        @DisplayName("Test saving  customer with valid request")
        public void testSave_Valid(PersonCustomer customer) throws ClientAlreadyExists {

            Mockito.when(customerRepository.findByCpf(customer.getCpf())).thenReturn(null);
            Mockito.when(customerRepository.saveAndFlush(customer)).thenReturn(customer);

            Long id = customerService.save(customer);

            assertEquals(customer.getId(), id);
        }

        @ParameterizedTest
        @MethodSource("getBusinessCustomerRequest")
        @DisplayName("Test saving customer with valid invalid customer")
        public void testSave_Invalid(PersonCustomer customer) {

            Mockito.when(customerRepository.findByCpf(customer.getCpf())).thenReturn(customer);
            assertThrows(ClientAlreadyExists.class, () -> customerService.save(customer));
        }

        public static Stream<Arguments> getBusinessCustomerRequest() {
            return Stream.of(
                    Arguments.of(PersonCustomer.builder()
                                    .cpf("05214532585")
                                    .mcc("2541")
                                    .email("test@test.com.br")
                            .id(1L)
                            .build()),
                    Arguments.of(PersonCustomer.builder()
                            .cpf("05214532585")
                            .mcc("2541")
                            .email("test@test.com.br")
                            .id(1L)
                            .build()));
        }

    }

    @Nested
    class UpdateCustomer {

        @ParameterizedTest
        @DisplayName("Test update customer valid request")
        @MethodSource("getBusinessCustomerRequest")
        public void testUpdateCustomer_Valid(Long id, PersonCustomer customer) throws ClientDoesNotExist {

            Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
            Mockito.when(customerRepository.saveAndFlush(customer)).thenReturn(customer);

            customerService.updateCustomer(id, customer);

        }

        @ParameterizedTest
        @DisplayName("Test update customer invalid")
        @MethodSource("getBusinessCustomerRequest")
        public void testUpdateCustomer_Invalid(Long id, PersonCustomer customer) {

            Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());
            assertThrows(ClientDoesNotExist.class, () -> customerService.updateCustomer(id, customer));
        }


        public static Stream<Arguments> getBusinessCustomerRequest() {
            return Stream.of(
                    Arguments.of(1L, PersonCustomer.builder()
                            .cpf("05214532585")
                            .mcc("2541")
                            .email("test@test.com.br")
                            .id(1L)
                            .build()),
                    Arguments.of(2l, PersonCustomer.builder()
                            .cpf("05214532585")
                            .mcc("2541")
                            .email("test@test.com.br")
                            .id(2L)
                            .build()));
        }

    }

}