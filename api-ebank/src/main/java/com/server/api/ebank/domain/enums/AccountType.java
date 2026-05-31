package com.server.api.ebank.domain.enums;

import lombok.Getter;

@Getter
public enum AccountType {
    CURRENT("Current"),
    DEPOSIT("Deposit"),
    CREDIT("Credit"),
    DEBIT("Debit");

    private final String message;

    AccountType(String message) {
        this.message = message;
    }
}
