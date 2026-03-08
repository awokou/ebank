package com.server.api.ebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.api.ebank.domain.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    Customer findByCin(String cin);

    boolean  existsByCin(String cin);
}
