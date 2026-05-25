package com.server.api.ebank.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirementDto {
    private Integer accountSender;
    private Integer accountReceiver;
    private BigDecimal amount;
    private String description;
    private boolean favorite;
    private String libel;
}
