package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.OperationType;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OperationDto {
    private Integer accountId;
    private BigDecimal amount;
    private String description;
    private OperationType type;
    private boolean favorite;
    private String libele;
}
