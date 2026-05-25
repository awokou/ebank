package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.AccountStatus;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Currency;

@Data
public class AccountDto {

    private String accountNumber;
    private BigDecimal balance;
    private Currency currency;
    private BigDecimal decisievert;

    private AccountStatus status;
    private Integer customerId;
}
