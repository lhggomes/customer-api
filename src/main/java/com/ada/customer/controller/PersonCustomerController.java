package com.ada.customer.controller;

import com.ada.customer.dto.BusinessCustomerDto;
import com.ada.customer.dto.PersonCustomerDto;
import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.entity.PersonCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;
import com.ada.customer.mapper.BusinessCustomerMapper;
import com.ada.customer.mapper.PersonCustomerMapper;
import com.ada.customer.services.interfaces.PersonCustomerService;
import com.ada.customer.validators.BusinessRuleFieldsValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "Customer Management", description = "Endpoints for managing customers")
@RestController
@RequiredArgsConstructor
@CrossOrigin
@Slf4j
@Validated
@RequestMapping(value = "/api/v1/customer")
public class PersonCustomerController {

    private final PersonCustomerService personCustomerService;
    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable Long id) {

        log.info("PROCESSING GET CUSTOMER: {}", id);
        Optional<PersonCustomer> customer = personCustomerService.getCustomer(id);
        if (customer.isPresent()) {
            PersonCustomerDto customerDto = PersonCustomerMapper.toPersonCustomerDto(customer.get());
            return new ResponseEntity<>(customerDto, HttpStatus.OK);
        }
        return new ResponseEntity<>(new HashMap<>(Map.of("response", "Nenhum cliente foi encontrado com este id")),
                HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {

        log.info("PROCESSING DELETE CUSTOMER: {}", id);
        personCustomerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HashMap<String, String>> update(@PathVariable Long id, @RequestBody @Valid PersonCustomerDto personCustomerDto) {

        log.info("PROCESSING UPDATE CUSTOMER: {} - {}", id, personCustomerDto);
        HashMap<String, String> validateResult = BusinessRuleFieldsValidator.validateBusinessCustomerDto(personCustomerDto);
        if (validateResult.isEmpty()) {

            try {
                PersonCustomer customer = PersonCustomerMapper.toPersonCustomer(personCustomerDto);
                personCustomerService.updateCustomer(id, customer);
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
    public ResponseEntity create(@RequestBody @Valid PersonCustomerDto personCustomer) {

        log.info("PROCESSING CREATE CUSTOMER:  {}", personCustomer);
        HashMap<String, String> validateResult = BusinessRuleFieldsValidator.validateBusinessCustomerDto(personCustomer);
        if (validateResult.isEmpty()) {
            try {
                PersonCustomer customer = PersonCustomerMapper.toPersonCustomer(personCustomer);
                Long idCustomerCreated = personCustomerService.save(customer);
                return new ResponseEntity<>(idCustomerCreated, HttpStatus.CREATED);
            } catch (ClientAlreadyExists ce) {
                log.error("CLIENT WITH CPF: {} ALREADY EXISTS", personCustomer.getCpf());
                return new ResponseEntity<>(new HashMap<>(Map.of("response",
                        "Um Cliente com este mesmo CPF já existe: " + personCustomer.getCpf())), HttpStatus.BAD_REQUEST);
            }

        }
        return new ResponseEntity<>(validateResult, HttpStatus.BAD_REQUEST);
    }


}
