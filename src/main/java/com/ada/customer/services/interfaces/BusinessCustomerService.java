package com.ada.customer.services.interfaces;

import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;

import java.util.Optional;

public interface BusinessCustomerService {
    Long save(BusinessCustomer businessCustomer) throws ClientAlreadyExists;
    Optional<BusinessCustomer> getCustomer(Long id);
    void deleteCustomer(Long id);
    void updateCustomer(Long id, BusinessCustomer businessCustomer) throws ClientDoesNotExist;
}
