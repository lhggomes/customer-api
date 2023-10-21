package com.ada.customer.services.interfaces;

import com.ada.customer.entity.BusinessCustomer;

import java.util.Optional;

public interface BusinessCustomerService {
    Long save(BusinessCustomer businessCustomer);
    Optional<BusinessCustomer> getCustomer(Long id);
    void deleteCustomer(Long id);
    void updateCustomer(Long id, BusinessCustomer businessCustomer);
}
