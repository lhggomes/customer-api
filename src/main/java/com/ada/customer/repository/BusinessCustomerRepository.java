package com.ada.customer.repository;

import com.ada.customer.entity.BusinessCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BusinessCustomerRepository extends JpaRepository<BusinessCustomer, Long> {
}
