package ru.stepanov.simulacrum.infrastructure.web.controller;

import org.springframework.web.bind.annotation.*;
import ru.stepanov.simulacrum.application.usecase.account.*;
import ru.stepanov.simulacrum.application.usecase.transaction.GetTransactionsUseCase;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.model.transaction.CardIndividualTransaction;
import ru.stepanov.simulacrum.domain.model.transaction.CardTransaction;
import ru.stepanov.simulacrum.domain.model.transaction.CashAccount;
import ru.stepanov.simulacrum.domain.model.transaction.Creditor;
import ru.stepanov.simulacrum.domain.model.transaction.Debtor;
import ru.stepanov.simulacrum.domain.model.transaction.MerchantInfo;
import ru.stepanov.simulacrum.domain.model.transaction.PaymentCard;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.infrastructure.web.dto.request.*;
import ru.stepanov.simulacrum.infrastructure.web.dto.response.TransactionPageResponse;
import ru.stepanov.simulacrum.infrastructure.web.dto.response.TransactionResponse;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final CreateAccountUseCase create;
    private final GetAccountsUseCase getAll;
    private final GetAccountUseCase getOne;
    private final ChangeAccountStatusUseCase change;
    private final GetTransactionsUseCase getTransactions;

    public AccountController(CreateAccountUseCase create, GetAccountsUseCase getAll, GetAccountUseCase getOne, ChangeAccountStatusUseCase change, GetTransactionsUseCase getTransactions) {
        this.create = create;
        this.getAll = getAll;
        this.getOne = getOne;
        this.change = change;
        this.getTransactions = getTransactions;
    }

    @PostMapping
    public Account create(@RequestBody CreateAccountRequest r) {
        return create.execute(r.getAccountId(), r.getCurrency(), r.getAccountType(), r.getAccountDescription());
    }

    @GetMapping
    public List<Account> all() {
        return getAll.execute();
    }

    @GetMapping("/{accountId}")
    public Account one(@PathVariable String accountId) {
        return getOne.execute(accountId);
    }

    @PatchMapping("/{accountId}/status")
    public Account status(@PathVariable String accountId, @RequestBody ChangeAccountStatusRequest r) {
        return change.execute(accountId, r.getStatus());
    }

    @GetMapping("/{accountId}/transactions")
    public TransactionPageResponse transactions(@PathVariable String accountId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.max(size, 1);
        getOne.execute(accountId);
        List<TransactionResponse> content = getTransactions.execute(accountId, normalizedPage, normalizedSize)
                .stream()
                .map(this::toResponse)
                .toList();
        long totalElements = getTransactions.count(accountId);
        int totalPages = totalElements == 0 ? 0 : (int) Math.ceil((double) totalElements / normalizedSize);
        return new TransactionPageResponse(content, normalizedPage, normalizedSize, totalElements, totalPages);
    }

    private TransactionResponse toResponse(TransactionHistory tx) {
        Money chargeAmount = tx.getChargeAmount();
        Debtor debtor = tx.getDebtor();
        Creditor creditor = tx.getCreditor();
        CashAccount debtorAccount = tx.getDebtorAccount();
        CashAccount creditorAccount = tx.getCreditorAccount();
        CardTransaction cardTransaction = tx.getCardTransaction();
        PaymentCard paymentCard = cardTransaction == null ? null : cardTransaction.getPaymentCard();
        CardIndividualTransaction cardIndividualTransaction = cardTransaction == null ? null : cardTransaction.getCardIndividualTransaction();
        MerchantInfo merchantInfo = cardIndividualTransaction == null ? null : cardIndividualTransaction.getMerchantTerminal();

        return new TransactionResponse(
                tx.getTransactionId(),
                tx.getAccountId(),
                tx.getStatus() == null ? null : tx.getStatus().name(),
                tx.getBankTransactionCode() == null ? null : tx.getBankTransactionCode().name(),
                tx.getBookingDateTime(),
                tx.getValueDateTime(),
                chargeAmount == null ? null : chargeAmount.getAmount(),
                chargeAmount == null ? null : chargeAmount.getCurrency(),
                debtor == null ? null : debtor.getName(),
                debtorAccount == null ? null : debtorAccount.getIdentification(),
                creditor == null ? null : creditor.getName(),
                creditorAccount == null ? null : creditorAccount.getIdentification(),
                merchantInfo == null ? null : merchantInfo.getMerchantName(),
                merchantInfo == null ? null : merchantInfo.getMerchantCategoryCode(),
                merchantInfo == null ? null : merchantInfo.getMerchantId(),
                paymentCard == null ? null : paymentCard.getMaskedPan(),
                paymentCard == null || paymentCard.getCardSchemeName() == null ? null : paymentCard.getCardSchemeName().name()
        );
    }
}
