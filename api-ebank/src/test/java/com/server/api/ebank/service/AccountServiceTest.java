package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.request.AccountDto;
import com.server.api.ebank.domain.entity.Account;
import com.server.api.ebank.domain.entity.Customer;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.AccountRepository;
import com.server.api.ebank.repository.CustomerRepository;
import com.server.api.ebank.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Test
    void testCreateSavingAccount_Success() {
        // Arrange
        AccountDto accountDto = new AccountDto(
                null,
                BigDecimal.valueOf(1000.0),
                null,
                BigDecimal.valueOf(500.0),
                null,
                1
        );

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        // Act
        AccountDto result = accountService.createAccount(accountDto);
        // Assert
        verify(accountRepository, times(1)).save(any(Account.class));
        assertNotNull(result);
        assertEquals(500.0, result.decisievert().doubleValue());
        assertEquals(1000.0, result.balance().doubleValue());
    }

    @Test
    void testCreateAccount_CustomerNotFound() {
        // Arrange
        AccountDto currentAccountDto = new AccountDto(
                null,
                BigDecimal.valueOf(1000.0),
                null,
                BigDecimal.valueOf(500.0),
                null,
                1
        );

        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            accountService.createAccount(currentAccountDto);
        });

        // Vérifie le message dynamique
        assertEquals("Customer with ID 1 not found", exception.getMessage());
        verify(accountRepository, never()).save(any(Account.class));
    }
}
