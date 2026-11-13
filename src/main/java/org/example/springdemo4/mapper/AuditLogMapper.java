package org.example.springdemo4.mapper;

import org.example.springdemo4.dto.AuditLogResponse;
import org.example.springdemo4.entity.AuditLogEntity;
import org.springframework.stereotype.Component;

@Component
public class AuditLogMapper {

    public AuditLogResponse toResponse(AuditLogEntity entity) {
        return new AuditLogResponse(entity.getId(), entity.getMessage(), entity.getCreatedAt());
    }
}