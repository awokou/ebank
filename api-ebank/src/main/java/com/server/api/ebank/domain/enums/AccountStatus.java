package com.server.api.ebank.domain.enums;

import lombok.Getter;

@Getter
public enum AccountStatus {
    ACTIVE("Active"),
    INACTIVE("In Active");

    private final String message;

    AccountStatus(String message) {
        this.message = message;
    }
}
