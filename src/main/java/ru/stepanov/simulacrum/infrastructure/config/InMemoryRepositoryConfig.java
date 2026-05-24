package ru.stepanov.simulacrum.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.repository.*;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class InMemoryRepositoryConfig {
    @Bean
    AccountRepository accountRepository() {
        Map<String, Account> map = new ConcurrentHashMap<>();
        return new AccountRepository() {
            public Optional<Account> findById(String id) {
                return Optional.ofNullable(map.get(id));
            }

            public Account save(Account a) {
                map.put(a.getAccountId(), a);
                return a;
            }

            public List<Account> findAll() {
                return new ArrayList<>(map.values());
            }
        };
    }

    @Bean
    TransactionHistoryRepository transactionHistoryRepository() {
        Map<String, TransactionHistory> map = new ConcurrentHashMap<>();
        return new TransactionHistoryRepository() {
            public Optional<TransactionHistory> findById(String id) {
                return Optional.ofNullable(map.get(id));
            }

            public TransactionHistory save(TransactionHistory tx) {
                map.put(tx.getTransactionId(), tx);
                return tx;
            }

            public List<TransactionHistory> findByAccountId(String a, int p, int s) {
                return map.values().stream().filter(t -> t.getAccountId().equals(a)).toList();
            }
        };
    }
}
