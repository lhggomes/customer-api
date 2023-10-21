package com.ada.customer.services.interfaces;

import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.entity.PersonCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;

import java.util.Optional;

public interface PersonCustomerService {

    Long save(PersonCustomer personCustomer) throws ClientAlreadyExists;
    Optional<PersonCustomer> getCustomer(Long id);
    void deleteCustomer(Long id);
    void updateCustomer(Long id, PersonCustomer personCustomer) throws ClientDoesNotExist;
}
