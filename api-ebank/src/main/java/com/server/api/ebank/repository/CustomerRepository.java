package com.server.api.ebank.repository;

import com.server.api.ebank.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByCin(String cin);

    boolean  existsByCin(String cin);
}
