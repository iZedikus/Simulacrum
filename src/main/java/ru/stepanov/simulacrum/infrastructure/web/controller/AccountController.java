package ru.stepanov.simulacrum.infrastructure.web.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.simulacrum.application.usecase.account.*;
import ru.stepanov.simulacrum.application.usecase.transaction.GetTransactionUseCase;
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
import ru.stepanov.simulacrum.domain.model.transaction.Unstructured;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.infrastructure.web.dto.request.*;
import ru.stepanov.simulacrum.infrastructure.web.dto.response.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final CreateAccountUseCase create;
    private final GetAccountsUseCase getAll;
    private final GetAccountUseCase getOne;
    private final ChangeAccountStatusUseCase change;
    private final GetTransactionsUseCase getTransactions;
    private final GetTransactionUseCase getTransaction;

    public AccountController(CreateAccountUseCase create, GetAccountsUseCase getAll, GetAccountUseCase getOne,
                             ChangeAccountStatusUseCase change, GetTransactionsUseCase getTransactions,
                             GetTransactionUseCase getTransaction) {
        this.create = create;
        this.getAll = getAll;
        this.getOne = getOne;
        this.change = change;
        this.getTransactions = getTransactions;
        this.getTransaction = getTransaction;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest r) {
        Account a = create.execute(r.getAccountId(), r.getCurrency(), r.getAccountType(), r.getAccountDescription());
        return toAccountResponse(a);
    }

    @GetMapping
    public AccountPageResponse all(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "20") int size) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.min(Math.max(size, 1), 100);
        List<AccountResponse> content = getAll.execute(normalizedPage, normalizedSize)
                .stream()
                .map(this::toAccountResponse)
                .toList();
        long totalElements = getAll.count();
        int totalPages = totalElements == 0 ? 0 : (int) Math.ceil((double) totalElements / normalizedSize);
        return new AccountPageResponse(content, new PageMetaResponse(normalizedPage, normalizedSize, totalElements, totalPages));
    }

    @GetMapping("/{accountId}")
    public AccountResponse one(@PathVariable String accountId) {
        return toAccountResponse(getOne.execute(accountId));
    }

    @PatchMapping("/{accountId}/status")
    public AccountResponse status(@PathVariable String accountId, @Valid @RequestBody ChangeAccountStatusRequest r) {
        return toAccountResponse(change.execute(accountId, r.getStatus()));
    }

    @GetMapping("/{accountId}/transactions")
    public TransactionPageResponse transactions(@PathVariable String accountId,
                                                @RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "20") int size,
                                                @RequestParam(required = false) Instant dateFrom,
                                                @RequestParam(required = false) Instant dateTo) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.max(size, 1);
        getOne.execute(accountId);
        List<TransactionResponse> content = getTransactions.execute(accountId, normalizedPage, normalizedSize)
                .stream()
                .filter(tx -> dateFrom == null || (tx.getBookingDateTime() != null && !tx.getBookingDateTime().isBefore(dateFrom)))
                .filter(tx -> dateTo == null || (tx.getBookingDateTime() != null && !tx.getBookingDateTime().isAfter(dateTo)))
                .map(this::toTransactionResponse)
                .toList();
        long totalElements = getTransactions.count(accountId);
        int totalPages = totalElements == 0 ? 0 : (int) Math.ceil((double) totalElements / normalizedSize);
        return new TransactionPageResponse(content, new PageMetaResponse(normalizedPage, normalizedSize, totalElements, totalPages));
    }

    @GetMapping("/{accountId}/transactions/{transactionId}")
    public TransactionResponse transaction(@PathVariable String accountId, @PathVariable String transactionId) {
        getOne.execute(accountId);
        TransactionHistory tx = getTransaction.execute(transactionId);
        return toTransactionResponse(tx);
    }

    private AccountResponse toAccountResponse(Account a) {
        return new AccountResponse(
                a.getAccountId(),
                a.getStatus(),
                a.getAccountType(),
                a.getCurrency(),
                a.getAccountDescription(),
                a.getStatusUpdateDateTime()
        );
    }

    private TransactionResponse toTransactionResponse(TransactionHistory tx) {
        Money chargeAmount = tx.getChargeAmount();
        Debtor debtor = tx.getDebtor();
        Creditor creditor = tx.getCreditor();
        CashAccount debtorAccount = tx.getDebtorAccount();
        CashAccount creditorAccount = tx.getCreditorAccount();
        CardTransaction cardTransaction = tx.getCardTransaction();
        PaymentCard paymentCard = cardTransaction == null ? null : cardTransaction.getPaymentCard();
        CardIndividualTransaction cardIndividualTransaction = cardTransaction == null ? null : cardTransaction.getCardIndividualTransaction();
        MerchantInfo merchantInfo = cardIndividualTransaction == null ? null : cardIndividualTransaction.getMerchantTerminal();
        Unstructured remittance = tx.getRemittanceInformation();

        String creditDebitIndicator = cardIndividualTransaction != null && cardIndividualTransaction.getCreditDebitIndicator() != null
                ? cardIndividualTransaction.getCreditDebitIndicator().name()
                : null;

        return new TransactionResponse(
                tx.getTransactionId(),
                tx.getAccountId(),
                tx.getStatus() == null ? null : tx.getStatus().name(),
                tx.getBankTransactionCode() == null ? null : tx.getBankTransactionCode().name(),
                creditDebitIndicator,
                tx.getBookingDateTime(),
                tx.getValueDateTime(),
                chargeAmount == null ? null : chargeAmount.getAmount(),
                chargeAmount == null ? null : chargeAmount.getCurrency(),
                new TransactionResponse.PartyResponse(
                        debtor == null ? null : debtor.getName(),
                        debtorAccount == null ? null : debtorAccount.getIdentification(),
                        debtorAccount == null || debtorAccount.getSchemeName() == null ? null : debtorAccount.getSchemeName().name()
                ),
                new TransactionResponse.PartyResponse(
                        creditor == null ? null : creditor.getName(),
                        creditorAccount == null ? null : creditorAccount.getIdentification(),
                        creditorAccount == null || creditorAccount.getSchemeName() == null ? null : creditorAccount.getSchemeName().name()
                ),
                merchantInfo == null ? null : new TransactionResponse.MerchantResponse(
                        merchantInfo.getMerchantName(),
                        merchantInfo.getMerchantId(),
                        merchantInfo.getMerchantCategoryCode()
                ),
                paymentCard == null ? null : new TransactionResponse.CardTransactionResponse(
                        paymentCard.getMaskedPan(),
                        paymentCard.getCardSchemeName() == null ? null : paymentCard.getCardSchemeName().name(),
                        paymentCard.getCardStatus() == null ? null : paymentCard.getCardStatus().name()
                ),
                remittance == null ? null : remittance.getUnstructured()
        );
    }
}
