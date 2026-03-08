package com.server.api.ebank.domain.dto.request;

import lombok.Data;

@Data
public class UpdateCardDto {
    private boolean value;
    private Integer customerId;
}
