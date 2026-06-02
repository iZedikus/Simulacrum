package ru.stepanov.simulacrum.infrastructure.persistence.mapper;

import ru.stepanov.simulacrum.domain.model.account.AccountStatus;
import ru.stepanov.simulacrum.domain.model.account.AccountType;
import ru.stepanov.simulacrum.domain.model.transaction.BankTransactionCode;
import ru.stepanov.simulacrum.domain.model.transaction.CardSchemeNameCode;
import ru.stepanov.simulacrum.domain.model.transaction.CardStatus;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionStatusCode;

final class ReferenceDataMapper {
    private ReferenceDataMapper() {
    }

    static short accountStatusId(AccountStatus status) {
        return switch (status) {
            case Enabled -> 1;
            case Disabled -> 2;
            case Deleted -> 3;
        };
    }

    static AccountStatus accountStatus(short id) {
        return switch (id) {
            case 1 -> AccountStatus.Enabled;
            case 2 -> AccountStatus.Disabled;
            case 3 -> AccountStatus.Deleted;
            default -> throw new IllegalArgumentException("Unknown account status id: " + id);
        };
    }

    static short accountTypeId(AccountType type) {
        return switch (type) {
            case Personal -> 1;
            case Business -> 2;
        };
    }

    static AccountType accountType(short id) {
        return switch (id) {
            case 1 -> AccountType.Personal;
            case 2 -> AccountType.Business;
            default -> throw new IllegalArgumentException("Unknown account type id: " + id);
        };
    }

    static short currencyId(String currency) {
        return switch (currency) {
            case "RUB" -> 643;
            case "USD" -> 840;
            case "EUR" -> 978;
            default -> throw new IllegalArgumentException("Unsupported currency code: " + currency);
        };
    }

    static String currency(short id) {
        return switch (id) {
            case 643 -> "RUB";
            case 840 -> "USD";
            case 978 -> "EUR";
            default -> throw new IllegalArgumentException("Unknown currency id: " + id);
        };
    }

    static short transactionStatusId(TransactionStatusCode status) {
        return switch (status) {
            case AcceptedSettlementCompleted -> 1;
            case AcceptedSettlementInProcess -> 2;
            case AcceptedWithoutPosting -> 3;
            case Pending -> 4;
            case Rejected -> 5;
        };
    }

    static TransactionStatusCode transactionStatus(short id) {
        return switch (id) {
            case 1 -> TransactionStatusCode.AcceptedSettlementCompleted;
            case 2 -> TransactionStatusCode.AcceptedSettlementInProcess;
            case 3 -> TransactionStatusCode.AcceptedWithoutPosting;
            case 4 -> TransactionStatusCode.Pending;
            case 5 -> TransactionStatusCode.Rejected;
            default -> throw new IllegalArgumentException("Unknown transaction status id: " + id);
        };
    }

    static short bankTransactionCodeId(BankTransactionCode code) {
        return switch (code) {
            case ObPayment -> 1;
            case Transfer, CashWithdrawal -> throw new IllegalArgumentException("Unsupported bank transaction code: " + code);
        };
    }

    static BankTransactionCode bankTransactionCode(short id) {
        return switch (id) {
            case 1 -> BankTransactionCode.ObPayment;
            default -> throw new IllegalArgumentException("Unknown bank transaction code id: " + id);
        };
    }

    static short cardSchemeId(CardSchemeNameCode code) {
        return switch (code) {
            case VISA -> 1;
            case MasterCard -> 2;
            case MIR -> 3;
        };
    }

    static CardSchemeNameCode cardScheme(short id) {
        return switch (id) {
            case 1 -> CardSchemeNameCode.VISA;
            case 2 -> CardSchemeNameCode.MasterCard;
            case 3 -> CardSchemeNameCode.MIR;
            default -> throw new IllegalArgumentException("Unknown card scheme id: " + id);
        };
    }

    static short cardStatusId(CardStatus status) {
        return switch (status) {
            case Active -> 1;
            case Expired -> 2;
            case Blocked -> 3;
        };
    }

    static CardStatus cardStatus(short id) {
        return switch (id) {
            case 1 -> CardStatus.Active;
            case 2 -> CardStatus.Expired;
            case 3 -> CardStatus.Blocked;
            default -> throw new IllegalArgumentException("Unknown card status id: " + id);
        };
    }
}
