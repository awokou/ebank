package com.server.api.ebank.domain.dto.request;

import com.server.api.ebank.domain.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDto {
    private Integer id;
    private String name;
    private String cin;
    private String email;
    private String address;
    private Gender gender;
    private String phoneNumber;
    private LocalDate birthDate;
}
