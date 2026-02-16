package com.server.api.ebank.service;

import com.server.api.ebank.dto.CurrentAccountDto;
import com.server.api.ebank.dto.SavingAccountDto;

public interface AccountService {

    CurrentAccountDto createCurrentAccount(CurrentAccountDto currentAccountDto);

    SavingAccountDto createSavingAccount(SavingAccountDto savingAccountDto);
}
