package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.Gender;
import jakarta.validation.constraints.*;

public record UserDto(
        Integer id,

        @NotBlank(message = "Name is required.")
        String name,

        @NotEmpty(message = "Email is required.")
        @Email(message = "Valid email is required.")
        String email,

        @NotBlank(message = "Password is required.")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,

        @NotNull(message = "Role is required.")
        String role,

        @NotNull(message = "Gender is required.")
        Gender gender,

        String phoneNumber
) {}
