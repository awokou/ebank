package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.AccountStatus;

import lombok.Data;

@Data
public class SavingAccountDto {
    private Integer accountId;
    private double balance;
    private AccountStatus status;
    private double interestRate;
    private Integer customerId;
}
