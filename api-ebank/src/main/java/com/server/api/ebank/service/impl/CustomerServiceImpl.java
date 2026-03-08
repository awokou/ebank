package com.server.api.ebank.service.impl;

import com.server.api.ebank.domain.dto.request.CustomerDto;
import com.server.api.ebank.domain.entity.Customer;
import com.server.api.ebank.exception.AlreadyExistException;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.CustomerRepository;
import com.server.api.ebank.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByCin(customerDto.getCin())) {
            // Message clair et uniforme pour les tests
            throw new AlreadyExistException(
                    String.format("Customer with CIN %s is already in use", customerDto.getCin()));
        }
        Customer customer = new Customer();
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setCin(customerDto.getCin());
        customer.setAddress(customerDto.getAddress());

        customerRepository.save(customer);
       
        return mapToCustomerDto(customer);
    }

    @Override
    @Transactional
    public CustomerDto findByCIN(String cin) {
        var customer = customerRepository.findByCin(cin);

        if (customer == null) {
            throw new ResourceNotFoundException("Customer with CIN " + cin + " not found");
        }

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setCin(customer.getCin());
        customerDto.setAddress(customer.getAddress());

        return customerDto;
    }

    @Override
    @Transactional
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::mapToCustomerDto)
                .toList();
    }

    @Override
    @Transactional
    public CustomerDto updateCustomer(Integer id, CustomerDto customerDto) {
        // Retrieve the customer or throw an exception if not found
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer is not exists with given id:" + id));

        // Update the customer entity with values from customerDto
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setCin(customer.getCin());
        customerDto.setAddress(customer.getAddress());
        customerRepository.save(customer);
        return mapToCustomerDto(customer);
    }

    @Override
    @Transactional
    public void deleteCustomerById(Integer id) {
        if (!customerRepository.existsById(id)) {
            throw new ResourceNotFoundException("Customer with id " + id + " does not exist");
        }
        customerRepository.deleteById(id);
    }

    /**
     * Map Customer entity to CustomerDto.
     *
     * @param customer the taxe entity
     * @return the customer data transfer object
     */
    private CustomerDto mapToCustomerDto(Customer customer) {

        CustomerDto customerDto = new CustomerDto();
        customerDto.setId(customer.getId());
        customerDto.setName(customer.getName());
        customerDto.setCin(customer.getCin());
        customerDto.setEmail(customer.getEmail());
        customerDto.setAddress(customer.getAddress());

        return customerDto;
    }
}
