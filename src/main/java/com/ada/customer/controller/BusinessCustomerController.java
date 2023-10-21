package com.ada.customer.controller;

import com.ada.customer.dto.BusinessCustomerDto;
import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.mapper.BusinessCustomerMapper;
import com.ada.customer.services.interfaces.BusinessCustomerService;
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
    public ResponseEntity<?> getById(@PathVariable Long id) {

        log.info("PROCESSING GET CUSTOMER: {}", id);
        Optional<BusinessCustomer> customer = businessCustomerService.getCustomer(id);
        if (customer.isPresent()){
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
    public ResponseEntity<Integer> update(@PathVariable Long id, @RequestBody @Valid BusinessCustomerDto customerDto) {

        log.info("PROCESSING UPDATE CUSTOMER: {} - {}", id, customerDto);
        BusinessCustomer customer = BusinessCustomerMapper.toBusinessCustomer(customerDto);
        businessCustomerService.updateCustomer(id, customer);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody @Valid BusinessCustomerDto customerDto) {

        log.info("PROCESSING CREATE CUSTOMER:  {}", customerDto);
        BusinessCustomer customer = BusinessCustomerMapper.toBusinessCustomer(customerDto);
        Long idCustomerCreated = businessCustomerService.save(customer);
        return new ResponseEntity<>(idCustomerCreated, HttpStatus.CREATED);
    }

}
