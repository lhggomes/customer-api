package com.ada.customer.services;

import com.ada.customer.CustomerApplication;
import com.ada.customer.entity.BusinessCustomer;
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

        public static Stream<Arguments> getCustomerId(){
            return Stream.of(Arguments.of(1L), Arguments.of(2L), Arguments.of(3L));
        }

    }


    @Test
    void deleteCustomer() {
    }

    @Test
    void save() {
    }

    @Test
    void updateCustomer() {
    }
}