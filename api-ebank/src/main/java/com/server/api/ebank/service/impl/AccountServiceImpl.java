package com.server.api.ebank.service.impl;

import com.server.api.ebank.domain.dto.request.AccountDto;
import com.server.api.ebank.domain.entity.Account;
import com.server.api.ebank.domain.entity.Customer;
import com.server.api.ebank.domain.enums.AccountStatus;
import com.server.api.ebank.exception.ResourceNotFoundException;
import com.server.api.ebank.repository.AccountRepository;
import com.server.api.ebank.repository.CustomerRepository;
import com.server.api.ebank.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import java.util.*;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final CustomerRepository customerRepository;
    private static final Random random = new Random();

    @Override
    @Transactional
    public AccountDto createAccount(AccountDto accountDto) {
        // Recherche du client
        Customer customer = customerRepository.findById(accountDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        String.format("Customer with ID %d not found", accountDto.getCustomerId())));

        Account saving = new Account();
        saving.setAccountNumber(generateIBAN());
        saving.setBalance(accountDto.getBalance());
        saving.setCurrency(accountDto.getCurrency());
        saving.setDecisievert(accountDto.getDecisievert());
        saving.setStatus(AccountStatus.CREATED);
        saving.setCustomer(customer);

        accountRepository.save(saving);

        return accountDto;
    }

    @Override
    public AccountDto getAccountById(Integer id) {
        return null;
    }

    @Override
    public List<AccountDto> getAccountsByCustomerId(Integer customerId) {
        return List.of();
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return List.of();
    }

    @Override
    public AccountDto updateAccount(Integer id, AccountDto accountDto) {
        return null;
    }

    @Override
    public void deleteAccountById(Integer id) {

    }

    /**
     * Generates a random International Bank Account Number (IBAN).
     *
     * @return The generated IBAN.
     */
    public static String generateIBAN() {
        String[] countryCodes = Locale.getISOCountries();
        int index = random.nextInt(countryCodes.length);
        String countryCode = countryCodes[index];
        String accountNumber = String.format("%02d-%04d-%04d-%04d-%04d",
                random.nextInt(100),
                random.nextInt(10000),
                random.nextInt(10000),
                random.nextInt(10000),
                random.nextInt(10000));
        return countryCode + accountNumber;
    }
}
