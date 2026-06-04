package ru.stepanov.simulacrum.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.SpringDataAccountRepo;
import ru.stepanov.simulacrum.infrastructure.persistence.mapper.AccountPersistenceMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaAccountRepositoryAdapter implements AccountRepository {
    private final SpringDataAccountRepo springDataRepo;
    private final AccountPersistenceMapper mapper;

    @Override
    public Optional<Account> findById(String id) {
        return springDataRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public Account save(Account account) {
        return mapper.toDomain(springDataRepo.save(mapper.toEntity(account)));
    }

    @Override
    public List<Account> findAll() {
        return springDataRepo.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    public List<Account> findAll(int page, int size) {
        return springDataRepo.findAll(PageRequest.of(page, size))
                .getContent()
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public long count() {
        return springDataRepo.count();
    }
}
