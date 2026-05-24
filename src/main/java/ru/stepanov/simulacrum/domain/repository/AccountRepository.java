package ru.stepanov.simulacrum.domain.repository;
import ru.stepanov.simulacrum.domain.model.account.Account;
import java.util.*;
public interface AccountRepository { Optional<Account> findById(String id); Account save(Account account); List<Account> findAll(); }
