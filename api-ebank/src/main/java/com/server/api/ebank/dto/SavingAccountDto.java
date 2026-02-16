package com.server.api.ebank.dto;

import com.server.api.ebank.entity.enums.AccountStatus;
import lombok.Data;

@Data
public class SavingAccountDto {
    private Integer accountId;
    private double balance;
    private AccountStatus status;
    private double interestRate;
    private Integer customerId;
}
