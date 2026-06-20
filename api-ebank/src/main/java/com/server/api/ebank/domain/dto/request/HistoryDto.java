package com.server.api.ebank.domain.dto.request;

import java.time.LocalDateTime;

public record HistoryDto(
        Integer id,
        String name,
        Integer userId,
        LocalDateTime createdAt
) {}
