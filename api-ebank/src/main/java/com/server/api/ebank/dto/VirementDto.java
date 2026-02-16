package com.server.api.ebank.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VirementDto {
    private Integer accountSender;
    private Integer accountReceiver;
    private double amount;
    private String description;
    private boolean favorite;
    private String libel;
}
