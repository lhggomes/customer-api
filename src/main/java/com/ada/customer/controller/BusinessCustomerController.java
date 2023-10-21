package com.ada.customer.controller;

import com.ada.customer.dto.BusinessCustomerDto;
import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;
import com.ada.customer.mapper.BusinessCustomerMapper;
import com.ada.customer.services.interfaces.BusinessCustomerService;
import com.ada.customer.validators.BusinessRuleFieldsValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@Validated
@RequestMapping("/api/v1/business-customer")
public class BusinessCustomerController {

    private final BusinessCustomerService businessCustomerService;

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {

        log.info("PROCESSING GET CUSTOMER: {}", id);
        Optional<BusinessCustomer> customer = businessCustomerService.getCustomer(id);
        if (customer.isPresent()) {
            BusinessCustomerDto customerDto = BusinessCustomerMapper.toBusinessCustomerDto(customer.get());
            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(new HashMap<>(Map.of("response", "Nenhum cliente foi encontrado com este id")),
                HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        log.info("PROCESSING DELETE CUSTOMER: {}", id);
        businessCustomerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, String>> update(@PathVariable Long id, @RequestBody @Valid BusinessCustomerDto customerDto) {

        log.info("PROCESSING UPDATE CUSTOMER: {} - {}", id, customerDto);
        HashMap<String, String> validateResult = BusinessRuleFieldsValidator.validateBusinessCustomerDto(customerDto);
        if (validateResult.isEmpty()) {

            try {
                BusinessCustomer customer = BusinessCustomerMapper.toBusinessCustomer(customerDto);
                businessCustomerService.updateCustomer(id, customer);
                return new ResponseEntity<>(HttpStatus.OK);
            } catch (ClientDoesNotExist cde) {
                log.error("CLIENT WITH ID: {} DOES NOT EXIST TO UPDATE", id);
                return new ResponseEntity<>(new HashMap<>(Map.of("response", "Não foi encontrado cliente para atualizar.")),
                        HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>(validateResult, HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity create(@RequestBody @Valid BusinessCustomerDto customerDto) {

        log.info("PROCESSING CREATE CUSTOMER:  {}", customerDto);
        HashMap<String, String> validateResult = BusinessRuleFieldsValidator.validateBusinessCustomerDto(customerDto);
        if (validateResult.isEmpty()) {
            try {
                BusinessCustomer customer = BusinessCustomerMapper.toBusinessCustomer(customerDto);
                Long idCustomerCreated = businessCustomerService.save(customer);
                return new ResponseEntity<>(idCustomerCreated, HttpStatus.CREATED);
            } catch (ClientAlreadyExists ce) {
                log.error("CLIENT WITH CNPJ: {} ALREADY EXISTS", customerDto.getCnpj());
                return new ResponseEntity<>(new HashMap<>(Map.of("response",
                        "Um Cliente com este mesmo CNPJ já existe: CNPJ: " + customerDto.getCnpj())), HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>(validateResult, HttpStatus.BAD_REQUEST);
    }

}
