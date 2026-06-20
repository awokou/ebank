package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.AccountType;
import java.math.BigDecimal;

public record OperationDto(
        Integer accountId,
        BigDecimal amount,
        String description,
        AccountType type,
        boolean favorite,
        String libel
) {}
