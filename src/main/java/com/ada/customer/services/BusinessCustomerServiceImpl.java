package com.ada.customer.services;

import com.ada.customer.entity.BusinessCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;
import com.ada.customer.repository.BusinessCustomerRepository;
import com.ada.customer.services.interfaces.BusinessCustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class BusinessCustomerServiceImpl implements BusinessCustomerService {

    private BusinessCustomerRepository businessCustomerRepository;

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
    public Long save(BusinessCustomer businessCustomer) throws ClientAlreadyExists {

        log.trace("PROCESSING SAVE BUSINESS CUSTOMER SERVICE");
        BusinessCustomer existCustomer = businessCustomerRepository.findByCnpj(businessCustomer.getCnpj());
        if (existCustomer != null){
            throw new ClientAlreadyExists(String.valueOf(existCustomer.getId()));
        }
        BusinessCustomer saved = businessCustomerRepository.saveAndFlush(businessCustomer);
        return saved.getId();
    }

    @Override
    public void updateCustomer(Long id, BusinessCustomer businessCustomer) throws ClientDoesNotExist{

        log.trace("PROCESSING UPDATE CUSTOMER SERVICE");
        Optional<BusinessCustomer> foundCustomer = businessCustomerRepository.findById(id);
        if (foundCustomer.isPresent()){

            BusinessCustomer customerToUpdate = foundCustomer.get();
            customerToUpdate.setMcc(businessCustomer.getMcc());
            customerToUpdate.setCnpj(businessCustomer.getCnpj());
            customerToUpdate.setBusinessContactCpf(businessCustomer.getBusinessContactCpf());
            customerToUpdate.setRazaoSocial(businessCustomer.getRazaoSocial());
            customerToUpdate.setBusinessContactName(businessCustomer.getBusinessContactName());
            customerToUpdate.setBusinessEmailName(businessCustomer.getBusinessEmailName());

            businessCustomerRepository.saveAndFlush(customerToUpdate);

        }else {
            throw new ClientDoesNotExist(String.valueOf(id));
        }
    }
}
