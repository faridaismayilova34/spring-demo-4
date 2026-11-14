package org.example.springdemo4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springdemo4.dto.response.AccountResponse;
import org.example.springdemo4.entity.AccountEntity;
import org.example.springdemo4.exception.InsufficientBalanceException;
import org.example.springdemo4.exception.NotFoundException;
import org.example.springdemo4.mapper.AccountMapper;
import org.example.springdemo4.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;


    @Transactional(propagation = Propagation.MANDATORY)
    public void debit(Long accountId, BigDecimal amount) {
        log.info("Debit method started. Account ID: {}", accountId);
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.error("Account not found. Account ID: {}", accountId);
                    return new NotFoundException("Account not found: " + accountId);
                });

        if (account.getBalance().compareTo(amount) < 0) {
            log.error("Insufficient balance. Account ID: {}", accountId);
            throw new InsufficientBalanceException("Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        log.info("Debit method completed successfully.");
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void credit(Long accountId, BigDecimal amount) {
        log.info("Credit method started. Account ID: {}", accountId);
        AccountEntity account = accountRepository.findById(accountId)
                .orElseThrow(() -> {
                    log.error("Account not found. Account ID: {}", accountId);
                    return new NotFoundException("Account not found: " + accountId);
                });

        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        log.info("Credit method completed successfully.");
    }

    @Transactional(readOnly = true)
    public AccountResponse getAccount(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toResponse)
                .orElseThrow(() -> new NotFoundException("Account not found: " + id));
    }
}
