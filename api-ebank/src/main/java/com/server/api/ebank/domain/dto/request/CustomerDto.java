package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {

    private Integer id;

    @NotBlank(message = "Name is required.")
    private String name;

    @NotBlank(message = "CIN is required.")
    private String cin;

    @NotBlank(message = "Email is required.")
    @Email(message = "Valid email is required.")
    private String email;

    @NotBlank(message = "Address is required.")
    private String address;

    @NotBlank(message = "Gender is required.")
    private Gender gender;

    @NotBlank(message = "Phone number is required.")
    private String phoneNumber;

    @Past(message = "Birth date must be in the past.")
    @NotBlank(message = "Birth date is required.")
    private LocalDate birthDate;
}
