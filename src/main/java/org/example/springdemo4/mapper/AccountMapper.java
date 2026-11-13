package org.example.springdemo4.mapper;

import org.example.springdemo4.dto.AccountResponse;
import org.example.springdemo4.entity.AccountEntity;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountResponse toResponse(AccountEntity entity) {
        return new AccountResponse(entity.getId(), entity.getOwnerName(), entity.getBalance());
    }
}