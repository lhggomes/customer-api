package com.ada.customer.services;

import com.ada.customer.CustomerApplication;
import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;
import com.ada.customer.repository.BusinessCustomerRepository;
import com.ada.customer.services.interfaces.BusinessCustomerService;
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

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Test for BusinessCustomerService Class")
public class BusinessCustomerServiceImplTest {

    @InjectMocks
    private BusinessCustomerServiceImpl businessCustomerService;

    @Mock
    private BusinessCustomerRepository businessCustomerRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Nested
    class GetCustomer {

        @ParameterizedTest
        @DisplayName("Test get Business customer with a valid return")
        @MethodSource("getCustomerId")
        public void testGetCustomer_Valid(Long id) {

            Mockito.when(businessCustomerRepository.findById(id)).thenReturn(Optional.of(BusinessCustomer.builder()
                    .razaoSocial("TEST")
                    .cnpj("05169759000124")
                    .mcc("1425")
                    .id(id)
                    .businessEmailName("test@test.com.br")
                    .businessContactCpf("02072674115")
                    .businessContactName("TEST")
                    .build()));


            Optional<BusinessCustomer> businessCustomer = businessCustomerService.getCustomer(id);

            assertTrue(businessCustomer.isPresent());
            assertEquals("1425", businessCustomer.get().getMcc());
            assertEquals("05169759000124", businessCustomer.get().getCnpj());
            assertEquals("test@test.com.br", businessCustomer.get().getBusinessEmailName());
            assertEquals("02072674115", businessCustomer.get().getBusinessContactCpf());
            assertEquals("TEST", businessCustomer.get().getBusinessContactName());

        }

        public static Stream<Arguments> getCustomerId() {
            return Stream.of(Arguments.of(1L), Arguments.of(2L), Arguments.of(3L));
        }

    }


    @Nested
    class DeleteCustomer {

        @ParameterizedTest
        @DisplayName("Test delete business customer with a valid response")
        @MethodSource("getCustomerId")
        public void testDeleteCustomer_Valid(Long id) {

            Mockito.doNothing().when(businessCustomerRepository).deleteById(id);
            businessCustomerService.deleteCustomer(id);

            Mockito.verify(businessCustomerRepository).deleteById(id);
        }

        public static Stream<Arguments> getCustomerId() {
            return Stream.of(Arguments.of(1L), Arguments.of(2L), Arguments.of(3L));
        }

    }

    @Nested
    class SaveCustomer {

        @ParameterizedTest
        @MethodSource("getBusinessCustomerRequest")
        @DisplayName("Test saving business customer with valid request")
        public void testSave_Valid(BusinessCustomer customer) throws ClientAlreadyExists {

            Mockito.when(businessCustomerRepository.findByCnpj(customer.getCnpj())).thenReturn(null);
            Mockito.when(businessCustomerRepository.saveAndFlush(customer)).thenReturn(customer);

            Long id = businessCustomerService.save(customer);

            assertEquals(customer.getId(), id);
        }

        @ParameterizedTest
        @MethodSource("getBusinessCustomerRequest")
        @DisplayName("Test saving business customer with valid invalid customer")
        public void testSave_Invalid(BusinessCustomer customer) {

            Mockito.when(businessCustomerRepository.findByCnpj(customer.getCnpj())).thenReturn(customer);
            assertThrows(ClientAlreadyExists.class, () -> businessCustomerService.save(customer));
        }

        public static Stream<Arguments> getBusinessCustomerRequest() {
            return Stream.of(
                    Arguments.of(BusinessCustomer.builder()
                            .businessContactName("TEST")
                            .businessContactCpf("33073975080")
                            .cnpj("67438898000155")
                            .businessEmailName("test@test.com.br")
                            .razaoSocial("TEST")
                            .mcc("1425")
                            .id(1L)
                            .build()),
                    Arguments.of(BusinessCustomer.builder()
                            .businessContactName("TEST_02")
                            .businessContactCpf("92248018037")
                            .cnpj("62826430000114")
                            .businessEmailName("test2@test.com.br")
                            .razaoSocial("TEST_02")
                            .mcc("1427")
                            .id(2L)
                            .build()));
        }

    }

    @Nested
    class UpdateCustomer {


        @ParameterizedTest
        @DisplayName("Test update customer valid request")
        @MethodSource("getBusinessCustomerRequest")
        public void testUpdateCustomer_Valid(Long id, BusinessCustomer customer) throws ClientDoesNotExist {

            Mockito.when(businessCustomerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
            Mockito.when(businessCustomerRepository.saveAndFlush(customer)).thenReturn(customer);

            businessCustomerService.updateCustomer(id, customer);

        }

        @ParameterizedTest
        @DisplayName("Test update customer invalid")
        @MethodSource("getBusinessCustomerRequest")
        public void testUpdateCustomer_Invalid(Long id, BusinessCustomer customer) {

            Mockito.when(businessCustomerRepository.findById(customer.getId())).thenReturn(Optional.empty());
            assertThrows(ClientDoesNotExist.class, () -> businessCustomerService.updateCustomer(id, customer));
        }


        public static Stream<Arguments> getBusinessCustomerRequest() {
            return Stream.of(
                    Arguments.of(1L, BusinessCustomer.builder()
                            .businessContactName("TEST")
                            .businessContactCpf("33073975080")
                            .cnpj("67438898000155")
                            .businessEmailName("test@test.com.br")
                            .razaoSocial("TEST")
                            .mcc("1425")
                            .id(1L)
                            .build()),
                    Arguments.of(2L, BusinessCustomer.builder()
                            .businessContactName("TEST_02")
                            .businessContactCpf("92248018037")
                            .cnpj("62826430000114")
                            .businessEmailName("test2@test.com.br")
                            .razaoSocial("TEST_02")
                            .mcc("1427")
                            .id(2L)
                            .build()));
        }

    }


}