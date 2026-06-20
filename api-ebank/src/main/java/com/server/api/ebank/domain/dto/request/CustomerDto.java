package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import java.time.LocalDate;

public record CustomerDto(
        Integer id,

        @NotBlank(message = "Name is required.")
        String name,

        @NotBlank(message = "CIN is required.")
        String cin,

        @NotBlank(message = "Email is required.")
        @Email(message = "Valid email is required.")
        String email,

        @NotBlank(message = "Address is required.")
        String address,

        @NotNull(message = "Gender is required.")
        Gender gender,

        @NotBlank(message = "Phone number is required.")
        String phoneNumber,

        @NotNull(message = "Birth date is required.")
        @Past(message = "Birth date must be in the past.")
        LocalDate birthDate
) {}
