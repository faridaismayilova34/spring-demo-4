package org.example.springdemo4.controller;

import lombok.RequiredArgsConstructor;
import org.example.springdemo4.dto.AccountResponse;
import org.example.springdemo4.service.AccountService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable Long id) {
        return accountService.getAccount(id);
    }
}