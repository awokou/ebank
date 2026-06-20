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
        if (customerRepository.existsByCin(customerDto.cin())) {
            // Message clair et uniforme pour les tests
            throw new AlreadyExistException(
                    String.format("Customer with CIN %s is already in use", customerDto.cin()));
        }
        Customer customer = new Customer();
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setCin(customerDto.cin());
        customer.setAddress(customerDto.address());
        customer.setBirthDate(customerDto.birthDate());
        customer.setGender(customerDto.gender());
        customer.setPhoneNumber(customerDto.phoneNumber());
        customer.setBirthDate(customerDto.birthDate());

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
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getCin(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getGender(),
                customer.getPhoneNumber(),
                customer.getBirthDate()
        );
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
        customer.setName(customerDto.name());
        customer.setEmail(customerDto.email());
        customer.setCin(customerDto.cin());
        customer.setAddress(customerDto.address());
        customer.setPhoneNumber(customerDto.phoneNumber());
        customer.setBirthDate(customerDto.birthDate());
        customer.setGender(customerDto.gender());
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
        return new CustomerDto(
                customer.getId(),
                customer.getName(),
                customer.getCin(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getGender(),
                customer.getPhoneNumber(),
                customer.getBirthDate()
        );
    }
}
