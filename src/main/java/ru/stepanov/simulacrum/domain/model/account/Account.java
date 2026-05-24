package ru.stepanov.simulacrum.domain.model.account;

import java.time.Instant;

public class Account {
    private final String accountId;
    private AccountStatus status;
    private Instant statusUpdateDateTime;
    private final String currency;
    private final AccountType accountType;
    private final String accountDescription;

    public Account(String accountId, String currency, AccountType accountType, String accountDescription) {
        this.accountId = accountId;
        this.currency = currency;
        this.accountType = accountType;
        this.accountDescription = accountDescription;
        this.status = AccountStatus.Enabled;
        this.statusUpdateDateTime = Instant.now();
    }

    public void enable() {
        status = AccountStatus.Enabled;
        statusUpdateDateTime = Instant.now();
    }

    public void disable() {
        status = AccountStatus.Disabled;
        statusUpdateDateTime = Instant.now();
    }

    public void delete() {
        status = AccountStatus.Deleted;
        statusUpdateDateTime = Instant.now();
    }

    public String getAccountId() {
        return accountId;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public Instant getStatusUpdateDateTime() {
        return statusUpdateDateTime;
    }

    public String getCurrency() {
        return currency;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getAccountDescription() {
        return accountDescription;
    }
}
