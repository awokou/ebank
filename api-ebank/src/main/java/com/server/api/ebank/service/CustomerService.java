package com.server.api.ebank.service;

import com.server.api.ebank.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto findByCIN(String cin);

    List<CustomerDto> getAllCustomers();

    CustomerDto updateCustomer(Integer id, CustomerDto customerDto);

    void deleteCustomerById(Integer id);
}
