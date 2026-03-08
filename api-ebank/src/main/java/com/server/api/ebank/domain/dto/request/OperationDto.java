package com.server.api.ebank.domain.dto.request;

import lombok.Data;

@Data
public class OperationDto {
    private Integer accountId;
    private double amount;
    private String description;
    private String accountType;
    private boolean favorite;
    private String libele;
}
