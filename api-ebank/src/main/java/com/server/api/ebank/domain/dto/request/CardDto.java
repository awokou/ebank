package com.server.api.ebank.domain.dto.request;

import lombok.Data;

@Data
public class CardDto {

    private Integer id;
    private boolean isEnabled;
    private boolean onlinePayment;
    private boolean internationalPayment;
    private boolean bypassed;
    private Integer customerId;
}
