package com.server.api.ebank.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryDto {
    private Integer id;
    private String name;
    private Integer userId;
    private LocalDateTime createdAt;
}
