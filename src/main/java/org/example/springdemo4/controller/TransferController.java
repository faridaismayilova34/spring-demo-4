package org.example.springdemo4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springdemo4.dto.request.TransferRequest;
import org.example.springdemo4.service.AccountService;
import org.example.springdemo4.service.TransferService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;
    private final AccountService accountService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public String transfer(@Valid @RequestBody TransferRequest request) {
        return transferService.transfer(request);
    }


    @PostMapping("/fail-test")
    @ResponseStatus(HttpStatus.OK)
    public String transferWithFailure(@Valid @RequestBody TransferRequest request) {
        return transferService.transferWithFailure(request);
    }

    // MANDATORY + transaction
    @PostMapping("/mandatory-test/with-transaction")
    @ResponseStatus(HttpStatus.OK)
    public String mandatoryTestWithTransaction(@Valid @RequestBody TransferRequest request) {
        return transferService.testMandatory(request);
    }

    // MANDATORY  -> IllegalTransactionStateException
    @PostMapping("/mandatory-test/without-transaction")
    @ResponseStatus(HttpStatus.OK)
    public String mandatoryTestWithoutTransaction(@Valid @RequestBody TransferRequest request) {
        accountService.debit(request.getFromAccountId(), request.getAmount());
        return "Mandatory test completed successfully.";
    }
}