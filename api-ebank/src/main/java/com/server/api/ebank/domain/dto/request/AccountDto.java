package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.AccountStatus;
import java.math.BigDecimal;
import java.util.Currency;

public record AccountDto(
        String accountNumber,
        BigDecimal balance,
        Currency currency,
        BigDecimal decisievert,
        AccountStatus status,
        Integer customerId
) {}
