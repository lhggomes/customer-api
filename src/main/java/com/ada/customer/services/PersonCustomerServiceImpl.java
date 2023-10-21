package com.ada.customer.services;

import com.ada.customer.entity.PersonCustomer;
import com.ada.customer.exceptions.ClientAlreadyExists;
import com.ada.customer.exceptions.ClientDoesNotExist;
import com.ada.customer.repository.PersonCustomerRepository;
import com.ada.customer.services.interfaces.PersonCustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class PersonCustomerServiceImpl implements PersonCustomerService {

    private PersonCustomerRepository personCustomerRepository;

    @Override
    public Long save(PersonCustomer personCustomer) throws ClientAlreadyExists {

        log.trace("PROCESSING SAVE BUSINESS CUSTOMER SERVICE");
        PersonCustomer existCustomer = personCustomerRepository.findByCpf(personCustomer.getCpf());
        if (existCustomer != null){
            throw new ClientAlreadyExists(String.valueOf(existCustomer.getId()));
        }

        PersonCustomer saved = personCustomerRepository.saveAndFlush(personCustomer);
        return saved.getId();
    }

    @Override
    public Optional<PersonCustomer> getCustomer(Long id) {

        log.trace("PROCESSING FIND PERSON CUSTOMER SERVICE: {}", id);
        Optional<PersonCustomer> customer = personCustomerRepository.findById(id);
        return customer;
    }

    @Override
    public void deleteCustomer(Long id) {

        log.trace("PROCESSING DELETE PERSON CUSTOMER SERVICE");
        personCustomerRepository.deleteById(id);
    }

    @Override
    public void updateCustomer(Long id, PersonCustomer personCustomer) throws ClientDoesNotExist {

        log.trace("PROCESSING UPDATE PERSON CUSTOMER SERVICE");
        Optional<PersonCustomer> foundCustomer = personCustomerRepository.findById(id);
        if (foundCustomer.isPresent()){

            PersonCustomer customerPersonToUpdate = foundCustomer.get();
            customerPersonToUpdate.setCpf(personCustomer.getCpf());
            customerPersonToUpdate.setName(personCustomer.getName());
            customerPersonToUpdate.setEmail(personCustomer.getEmail());
            customerPersonToUpdate.setMcc(personCustomer.getMcc());

            personCustomerRepository.saveAndFlush(customerPersonToUpdate);

        } else {
            throw new ClientDoesNotExist(String.valueOf(id));
        }

    }
}
