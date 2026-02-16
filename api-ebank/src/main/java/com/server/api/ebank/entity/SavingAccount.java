package com.server.api.ebank.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value = "SA")
public class SavingAccount extends Account {

    private double interestRate;
    
    private double balance;
}
