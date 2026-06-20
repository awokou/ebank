package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.request.CustomerDto;
import com.server.api.ebank.domain.entity.Customer;
import com.server.api.ebank.exception.AlreadyExistException;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.CustomerRepository;
import com.server.api.ebank.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Test
    void testCreateCustomer() {
        CustomerDto customerDto = new CustomerDto(
                null,
                "John Doe",
                "123456789",
                "john.doe@example.com",
                "123 Main St",
                null,
                null,
                null
        );

        Customer customer = new Customer();
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setCin("123456789");
        customer.setAddress("123 Main St");

        when(customerRepository.existsByCin(customerDto.cin())).thenReturn(false);
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> {
            Customer saved = invocation.getArgument(0);
            saved.setId(1);
            return saved;
        });

        CustomerDto result = customerService.createCustomer(customerDto);

        assertNotNull(result);
        assertEquals("John Doe", result.name());
        assertEquals("123456789", result.cin());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testCreateCustomer_AlreadyExists() {
        CustomerDto customerDto = new CustomerDto(
                null,
                null,
                "123456789",
                null,
                null,
                null,
                null,
                null
        );

        when(customerRepository.existsByCin("123456789")).thenReturn(true);

        AlreadyExistException exception = assertThrows(
                AlreadyExistException.class,
                () -> customerService.createCustomer(customerDto));

        assertEquals("Customer with CIN 123456789 is already in use", exception.getMessage());
        verify(customerRepository, times(1)).existsByCin("123456789");
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testGetAllCustomer() {
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Awokou Mathieu");
        customerList.add(customer);

        when(customerRepository.findAll()).thenReturn(customerList);

        List<CustomerDto> result = customerService.getAllCustomers();
        assertEquals(1, result.size());
        assertEquals("Awokou Mathieu", result.get(0).name());
    }

    @Test
    void testUpdateCustomer() {
        Integer id = 1;
        CustomerDto customerDto = new CustomerDto(
                null,
                "John Doe",
                "123456789",
                "john.doe@example.com",
                "123 Main St",
                null,
                null,
                null
        );

        Customer customer = new Customer();
        customer.setId(id);
        customer.setName("John Doe");
        customer.setEmail("john.doe@example.com");
        customer.setCin("123456789");
        customer.setAddress("123 Main St");
        customer.setCreatedAt(LocalDateTime.now());

        // Mock repository
        when(customerRepository.findById(id)).thenReturn(Optional.of(customer));
        when(customerRepository.save(any(Customer.class))).thenAnswer(invocation -> invocation.getArgument(0));

        CustomerDto updatedCustomer = customerService.updateCustomer(id, customerDto);

        assertNotNull(updatedCustomer);
        assertEquals("John Doe", updatedCustomer.name());
        verify(customerRepository).save(any(Customer.class));
    }

    @Test
    void testCustomerNotFound() {
        Integer id = 1;
        CustomerDto customerDto = new CustomerDto(
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        when(customerRepository.findById(id)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> customerService.updateCustomer(id, customerDto));

        assertEquals("Customer is not exists with given id:" + id, exception.getMessage());
        verify(customerRepository, never()).save(any(Customer.class));
    }

    @Test
    void testFindByCIN_Success() {
        // Arrange
        String cin = "ABC123";
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");
        customer.setEmail("john@example.com");
        customer.setCin(cin);
        customer.setAddress("123 Street");

        // Le repository renvoie l'entité Customer
        when(customerRepository.findByCin(cin)).thenReturn(customer);

        // Act
        CustomerDto customerDto = customerService.findByCIN(cin);

        // Assert
        assertNotNull(customerDto);
        assertEquals("John Doe", customerDto.name());
        assertEquals(cin, customerDto.cin());
        assertEquals("123 Street", customerDto.address());
    }

    @Test
    void testFindByCIN_CustomerNotFound() {
        String cin = "XYZ999";
        when(customerRepository.findByCin(cin)).thenReturn(null);

        assertThrows(ResourceNotFoundException.class, () -> customerService.findByCIN(cin));
        verify(customerRepository, times(1)).findByCin(cin);
    }

    @Test
    void testDeleteCustomerByIdSuccess() {
        when(customerRepository.existsById(1)).thenReturn(true);
        customerService.deleteCustomerById(1);
        verify(customerRepository, times(1)).deleteById(1);
    }

    @Test
    void testDeleteCustomerByIdNotFound() {
        when(customerRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> customerService.deleteCustomerById(1));
        verify(customerRepository, never()).deleteById(1);
    }
}
