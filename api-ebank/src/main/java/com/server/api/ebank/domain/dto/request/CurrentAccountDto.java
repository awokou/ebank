package com.server.api.ebank.domain.dto.request;

import lombok.Data;

@Data
public class CurrentAccountDto {
    private double balance;
    private double decouvert;
    private Integer customerId;
}
