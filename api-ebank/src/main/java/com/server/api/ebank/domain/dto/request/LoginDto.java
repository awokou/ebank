package com.server.api.ebank.domain.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginDto(
        @NotBlank(message = "Email is required")
        String email,

        @NotBlank(message = "Password is required")
        String password
) {}
