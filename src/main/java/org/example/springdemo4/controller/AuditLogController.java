package org.example.springdemo4.controller;

import lombok.RequiredArgsConstructor;
import org.example.springdemo4.dto.response.AuditLogResponse;
import org.example.springdemo4.service.AuditLogService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public List<AuditLogResponse> getAll() {
        return auditLogService.getAll();
    }
}