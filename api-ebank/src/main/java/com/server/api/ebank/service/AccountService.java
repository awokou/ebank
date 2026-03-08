package com.server.api.ebank.service;

import com.server.api.ebank.domain.dto.request.CurrentAccountDto;
import com.server.api.ebank.domain.dto.request.SavingAccountDto;

public interface AccountService {

    CurrentAccountDto createCurrentAccount(CurrentAccountDto currentAccountDto);

    SavingAccountDto createSavingAccount(SavingAccountDto savingAccountDto);
}
