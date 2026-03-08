package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.request.CurrentAccountDto;
import com.server.api.ebank.domain.dto.request.SavingAccountDto;
import com.server.api.ebank.domain.entity.CurrentAccount;
import com.server.api.ebank.domain.entity.Customer;
import com.server.api.ebank.domain.entity.SavingAccount;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.AccountRepository;
import com.server.api.ebank.repository.CustomerRepository;
import com.server.api.ebank.service.impl.AccountServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
    void testCreateCurrentAccount() {
        // Arrange
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        currentAccountDto.setDecouvert(500.0);
        currentAccountDto.setBalance(1000.0);
        currentAccountDto.setCustomerId(1);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("John Doe");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        // Act
        CurrentAccountDto result = accountService.createCurrentAccount(currentAccountDto);
        // Assert
        verify(accountRepository, times(1)).save(any(CurrentAccount.class));
        assertNotNull(result);
        assertEquals(500.0, result.getDecouvert());
        assertEquals(1000.0, result.getBalance());
    }

    @Test
    void testCreateCurrentAccount_CustomerNotFound() {
        // Arrange
        CurrentAccountDto currentAccountDto = new CurrentAccountDto();
        currentAccountDto.setDecouvert(500.0);
        currentAccountDto.setBalance(1000.0);
        currentAccountDto.setCustomerId(1);

        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            accountService.createCurrentAccount(currentAccountDto);
        });

        // Vérifie le message dynamique
        assertEquals("Customer with ID 1 not found", exception.getMessage());
        verify(accountRepository, never()).save(any(CurrentAccount.class));
    }

    @Test
    void testCreateSavingAccount_Success() {
        // Arrange
        SavingAccountDto savingAccountDto = new SavingAccountDto();
        savingAccountDto.setInterestRate(2.5);
        savingAccountDto.setBalance(1500.0);
        savingAccountDto.setCustomerId(1);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("Jane Doe");

        when(customerRepository.findById(1)).thenReturn(Optional.of(customer));
        // Act
        SavingAccountDto result = accountService.createSavingAccount(savingAccountDto);
        // Assert
        verify(accountRepository, times(1)).save(any(SavingAccount.class));
        assertNotNull(result);
        assertEquals(2.5, result.getInterestRate());
        assertEquals(1500.0, result.getBalance());
    }

    @Test
    void testCreateSavingAccount_CustomerNotFound() {
        // Arrange
        SavingAccountDto savingAccountDto = new SavingAccountDto();
        savingAccountDto.setInterestRate(2.5);
        savingAccountDto.setBalance(1500.0);
        savingAccountDto.setCustomerId(1);

        when(customerRepository.findById(1)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            accountService.createSavingAccount(savingAccountDto);
        });

        // Vérifie le message dynamique
        assertEquals("Customer with ID 1 not found", exception.getMessage());
        verify(accountRepository, never()).save(any(SavingAccount.class));
    }
}
