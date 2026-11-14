package org.example.springdemo4.dto.response;

import java.time.LocalDateTime;

public record AuditLogResponse(Long id, String message, LocalDateTime createdAt) {
}