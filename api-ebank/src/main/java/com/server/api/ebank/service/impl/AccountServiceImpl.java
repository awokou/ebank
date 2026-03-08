package com.server.api.ebank.service.impl;

import com.server.api.ebank.domain.dto.request.CurrentAccountDto;
import com.server.api.ebank.domain.dto.request.SavingAccountDto;
import com.server.api.ebank.domain.entity.CurrentAccount;
import com.server.api.ebank.domain.entity.Customer;
import com.server.api.ebank.domain.entity.SavingAccount;
import com.server.api.ebank.domain.enums.AccountStatus;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.AccountRepository;
import com.server.api.ebank.repository.CustomerRepository;
import com.server.api.ebank.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;

    @Override
    @Transactional
    public CurrentAccountDto createCurrentAccount(CurrentAccountDto currentAccountDto) {
        // Recherche du client
        Customer customer = customerRepository.findById(currentAccountDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Customer with ID %d not found", currentAccountDto.getCustomerId())));
        CurrentAccount currentAccount = new CurrentAccount();
        currentAccount.setDecisievert(currentAccountDto.getDecouvert());
        currentAccount.setBalance(currentAccountDto.getBalance());
        currentAccount.setStatus(AccountStatus.CREATED);
        currentAccount.setCustomer(customer);
        accountRepository.save(currentAccount);

        return currentAccountDto;
    }

    @Override
    @Transactional
    public SavingAccountDto createSavingAccount(SavingAccountDto savingAccountDto) {
        // Recherche du client
        Customer customer = customerRepository.findById(savingAccountDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Customer with ID %d not found", savingAccountDto.getCustomerId())));
        SavingAccount saving = new SavingAccount();
        saving.setInterestRate(savingAccountDto.getInterestRate());
        saving.setBalance(savingAccountDto.getBalance());
        saving.setStatus(AccountStatus.CREATED);
        saving.setCustomer(customer);
        accountRepository.save(saving);

        return savingAccountDto;
    }
}
