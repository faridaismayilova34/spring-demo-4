package org.example.springdemo4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springdemo4.dto.request.TransferRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransferService {

    private final AccountService accountService;
    private final AuditLogService auditLogService;

    @Transactional(propagation = Propagation.REQUIRED)
    public String transfer(TransferRequest request) {
        return doTransfer(request, false);
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public String transferWithFailure(TransferRequest request) {
        return doTransfer(request, true);
    }

    private String doTransfer(TransferRequest request, boolean simulateFailure) {
        log.info("Transfer method started.");
        auditLogService.saveLog("Transfer attempted");

        accountService.debit(request.getFromAccountId(), request.getAmount());
        accountService.credit(request.getToAccountId(), request.getAmount());

        if (simulateFailure) {
            throw new RuntimeException("Transfer failed for testing");
        }
        log.info("Transfer completed successfully.");
        return "Transfer completed successfully.";
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public String testMandatory(TransferRequest request) {
        log.info("Mandatory test started.");
        accountService.debit(request.getFromAccountId(), request.getAmount());
        log.info("Mandatory test completed successfully.");
        return "Mandatory test completed successfully.";
    }
}
