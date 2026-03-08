package com.server.api.ebank.domain.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@Entity
@EqualsAndHashCode(callSuper = true)
@DiscriminatorValue(value = "CA")
public class CurrentAccount extends Account {

    private double decisievert;
    
    private double balance;
}
