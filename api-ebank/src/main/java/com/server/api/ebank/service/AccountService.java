package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.request.AccountDto;
import java.util.List;

public interface AccountService {

    AccountDto createAccount(AccountDto accountDto);

    AccountDto getAccountById(Integer id);

    List<AccountDto> getAccountsByCustomerId(Integer customerId);

    List<AccountDto> getAllAccounts();

    AccountDto updateAccount(Integer id, AccountDto accountDto);

    void deleteAccountById(Integer id);
}
