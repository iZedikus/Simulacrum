package ru.stepanov.simulacrum.domain.model.account;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class Account {
    private final String accountId;
    @Setter(AccessLevel.PRIVATE)
    private AccountStatus status = AccountStatus.Enabled;
    @Setter(AccessLevel.PRIVATE)
    private Instant statusUpdateDateTime = Instant.now();
    private final String currency;
    private final AccountType accountType;
    private final String accountDescription;

    public void enable() {
        setStatus(AccountStatus.Enabled);
        setStatusUpdateDateTime(Instant.now());
    }

    public void disable() {
        setStatus(AccountStatus.Disabled);
        setStatusUpdateDateTime(Instant.now());
    }

    public void delete() {
        setStatus(AccountStatus.Deleted);
        setStatusUpdateDateTime(Instant.now());
    }
}
