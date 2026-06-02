package ru.stepanov.simulacrum.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.domain.model.transaction.*;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.CardTransactionJpaEntity;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.TransactionHistoryJpaEntity;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable.CashAccountEmbeddable;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable.PartyEmbeddable;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable.RemittanceInfoEmbeddable;

@Component
public class TransactionHistoryPersistenceMapper {
    public TransactionHistoryJpaEntity toEntity(TransactionHistory transaction) {
        Money chargeAmount = transaction.getChargeAmount();
        String chargeCurrency = chargeAmount == null ? null : chargeAmount.getCurrency();
        return new TransactionHistoryJpaEntity(
                transaction.getTransactionId(),
                transaction.getAccountId(),
                ReferenceDataMapper.transactionStatusId(transaction.getStatus()),
                transaction.getStatus().name(),
                ReferenceDataMapper.bankTransactionCodeId(transaction.getBankTransactionCode()),
                transaction.getBankTransactionCode().name(),
                transaction.getBookingDateTime(),
                transaction.getValueDateTime(),
                chargeAmount == null ? null : chargeAmount.getAmount(),
                chargeCurrency == null ? null : ReferenceDataMapper.currencyId(chargeCurrency),
                chargeCurrency,
                toParty(transaction.getDebtor()),
                toParty(transaction.getCreditor()),
                toEmbeddable(transaction.getDebtorAccount()),
                toEmbeddable(transaction.getCreditorAccount()),
                toEmbeddable(transaction.getRemittanceInformation()),
                toEntity(transaction.getCardTransaction())
        );
    }

    public TransactionHistory toDomain(TransactionHistoryJpaEntity entity) {
        return new TransactionHistory(
                entity.getTransactionId(),
                entity.getAccountId(),
                entity.getStatusId() == null ? TransactionStatusCode.valueOf(entity.getStatus()) : ReferenceDataMapper.transactionStatus(entity.getStatusId()),
                entity.getBankTransactionCodeId() == null ? BankTransactionCode.valueOf(entity.getBankTransactionCode()) : ReferenceDataMapper.bankTransactionCode(entity.getBankTransactionCodeId()),
                entity.getBookingDateTime(),
                entity.getValueDateTime(),
                entity.getChargeAmount() == null ? null : new Money(entity.getChargeAmount(), entity.getChargeCurrency()),
                entity.getRemittance() == null ? null : new Unstructured(entity.getRemittance().getUnstructured()),
                null,
                null,
                toCreditor(entity.getCreditor()),
                toDebtor(entity.getDebtor()),
                toCashAccount(entity.getCreditorAccount()),
                toCashAccount(entity.getDebtorAccount()),
                toDomain(entity.getCardTransaction())
        );
    }

    private PartyEmbeddable toParty(Debtor debtor) {
        if (debtor == null) {
            return null;
        }
        return toParty(debtor.getName(), debtor.getPostalAddress());
    }

    private PartyEmbeddable toParty(Creditor creditor) {
        if (creditor == null) {
            return null;
        }
        return toParty(creditor.getName(), creditor.getPostalAddress());
    }

    private PartyEmbeddable toParty(String name, PostalAddress12 address) {
        if (address == null) {
            return new PartyEmbeddable(name, null, null, null, null, null);
        }
        return new PartyEmbeddable(
                name,
                address.getStreetName(),
                address.getBuildingNumber(),
                address.getPostCode(),
                address.getTownName(),
                address.getCountry()
        );
    }

    private Debtor toDebtor(PartyEmbeddable debtor) {
        if (isEmptyParty(debtor)) {
            return null;
        }
        return new Debtor(debtor.getName(), java.util.List.of(), null, toPostalAddress(debtor));
    }

    private Creditor toCreditor(PartyEmbeddable creditor) {
        if (isEmptyParty(creditor)) {
            return null;
        }
        return new Creditor(creditor.getName(), java.util.List.of(), null, toPostalAddress(creditor));
    }

    private boolean isEmptyParty(PartyEmbeddable party) {
        return party == null || (party.getName() == null && party.getStreetName() == null && party.getBuildingNumber() == null
                && party.getPostCode() == null && party.getTownName() == null && party.getCountry() == null);
    }

    private PostalAddress12 toPostalAddress(PartyEmbeddable party) {
        if (party.getStreetName() == null && party.getBuildingNumber() == null && party.getPostCode() == null
                && party.getTownName() == null && party.getCountry() == null) {
            return null;
        }
        return new PostalAddress12(party.getStreetName(), party.getBuildingNumber(), party.getPostCode(), party.getTownName(), party.getCountry(), java.util.List.of());
    }

    private CashAccountEmbeddable toEmbeddable(CashAccount account) {
        if (account == null) {
            return null;
        }
        return new CashAccountEmbeddable(account.getName(), account.getSchemeName().name(), account.getIdentification());
    }

    private CashAccount toCashAccount(CashAccountEmbeddable account) {
        if (account == null || (account.getAccountName() == null && account.getAccountScheme() == null && account.getAccountIdentification() == null)) {
            return null;
        }
        return new CashAccount(account.getAccountName(), AccountIdentificationCode.valueOf(account.getAccountScheme()), account.getAccountIdentification());
    }

    private RemittanceInfoEmbeddable toEmbeddable(Unstructured remittance) {
        if (remittance == null) {
            return null;
        }
        return new RemittanceInfoEmbeddable(remittance.getUnstructured());
    }

    private CardTransactionJpaEntity toEntity(CardTransaction cardTransaction) {
        if (cardTransaction == null) {
            return null;
        }
        PaymentCard paymentCard = cardTransaction.getPaymentCard();
        return new CardTransactionJpaEntity(
                cardTransaction.getCardTransactionId(),
                null,
                ReferenceDataMapper.cardSchemeId(paymentCard.getCardSchemeName()),
                paymentCard.getMaskedPan(),
                paymentCard.getExpiryDate(),
                paymentCard.getAdditionalCardData(),
                ReferenceDataMapper.cardStatusId(paymentCard.getCardStatus())
        );
    }

    private CardTransaction toDomain(CardTransactionJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        PaymentCard paymentCard = new PaymentCard(
                entity.getMaskedPan(),
                ReferenceDataMapper.cardScheme(entity.getCardSchemeId()),
                entity.getExpiryDate(),
                entity.getAdditionalCardData(),
                ReferenceDataMapper.cardStatus(entity.getCardStatusId())
        );
        return new CardTransaction(entity.getCardTransactionId(), paymentCard, null);
    }
}
