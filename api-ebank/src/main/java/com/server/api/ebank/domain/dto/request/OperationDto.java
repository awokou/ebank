package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperationDto {
    private Integer accountId;
    private BigDecimal amount;
    private String description;
    private AccountType type;
    private boolean favorite;
    private String libel;
}
