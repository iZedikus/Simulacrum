package ru.stepanov.simulacrum.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.AccountJpaEntity;

@Component
public class AccountPersistenceMapper {
    public AccountJpaEntity toEntity(Account account) {
        return new AccountJpaEntity(
                account.getAccountId(),
                ReferenceDataMapper.accountStatusId(account.getStatus()),
                ReferenceDataMapper.accountTypeId(account.getAccountType()),
                account.getStatusUpdateDateTime(),
                ReferenceDataMapper.currencyId(account.getCurrency()),
                account.getAccountDescription()
        );
    }

    public Account toDomain(AccountJpaEntity entity) {
        return new Account(
                entity.getAccountId(),
                ReferenceDataMapper.accountStatus(entity.getStatusId()),
                entity.getStatusUpdateDateTime(),
                ReferenceDataMapper.currency(entity.getCurrencyId()),
                ReferenceDataMapper.accountType(entity.getAccountTypeId()),
                entity.getAccountDescription()
        );
    }
}
