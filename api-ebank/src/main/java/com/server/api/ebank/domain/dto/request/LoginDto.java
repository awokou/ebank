package com.server.api.ebank.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Email require")
    private String email;

    @NotBlank(message = "Password require")
    private String password;
}
