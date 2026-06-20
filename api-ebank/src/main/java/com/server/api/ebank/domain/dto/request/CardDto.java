package com.server.api.ebank.domain.dto.request;

public record CardDto(
        Integer id,
        boolean isBlocked,
        boolean onlinePayment,
        boolean internationalPayment,
        boolean bypassed,
        Integer customerId
) {}
