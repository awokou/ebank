package com.server.api.ebank.service;

import java.util.List;

import com.server.api.ebank.domain.dto.request.CustomerDto;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customerDto);

    CustomerDto findByCIN(String cin);

    List<CustomerDto> getAllCustomers();

    CustomerDto updateCustomer(Integer id, CustomerDto customerDto);

    void deleteCustomerById(Integer id);
}
