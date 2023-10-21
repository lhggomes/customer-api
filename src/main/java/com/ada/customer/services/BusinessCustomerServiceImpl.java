package com.ada.customer.services;

import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.repository.BusinessCustomerRepository;
import com.ada.customer.services.interfaces.BusinessCustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Optional;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class BusinessCustomerServiceImpl implements BusinessCustomerService {

    private BusinessCustomerRepository businessCustomerRepository;

    @Override
    public Long save(BusinessCustomer businessCustomer) {
        log.trace("PROCESSING SAVE BUSINESS CUSTOMER SERVICE");
        BusinessCustomer saved = businessCustomerRepository.save(businessCustomer);
        return saved.getId();
    }

    @Override
    public Optional<BusinessCustomer> getCustomer(Long id) {
        log.trace("PROCESSING FIND CUSTOMER SERVICE: {}", id);
        Optional<BusinessCustomer> customer = businessCustomerRepository.findById(id);
        return customer;
    }

    @Override
    public void deleteCustomer(Long id) {
        log.trace("PROCESSING DELETE CUSTOMER SERVICE");
        businessCustomerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Long id, BusinessCustomer businessCustomer) {

        Optional<BusinessCustomer> foundCustomer = businessCustomerRepository.findById(id);


    }
}
