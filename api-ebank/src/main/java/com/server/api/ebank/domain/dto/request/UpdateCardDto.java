package com.server.api.ebank.domain.dto.request;

public record UpdateCardDto(
        boolean value,
        Integer customerId
) {}
