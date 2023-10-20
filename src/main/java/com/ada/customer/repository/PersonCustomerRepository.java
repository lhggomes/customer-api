package com.ada.customer.repository;

import com.ada.customer.entity.PersonCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonCustomerRepository extends JpaRepository<PersonCustomer, Long> {
}
