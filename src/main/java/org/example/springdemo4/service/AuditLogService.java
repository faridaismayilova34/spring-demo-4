package org.example.springdemo4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springdemo4.dto.AuditLogResponse;

import org.example.springdemo4.entity.AuditLogEntity;
import org.example.springdemo4.mapper.AuditLogMapper;
import org.example.springdemo4.repository.AuditLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final AuditLogMapper auditLogMapper;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveLog(String message) {
        log.info("Save audit log method started.");
        AuditLogEntity entity = new AuditLogEntity();
        entity.setMessage(message);
        entity.setCreatedAt(LocalDateTime.now());
        auditLogRepository.save(entity);
        log.info("Audit log saved successfully.");
    }
    @Transactional(readOnly = true)
    public List<AuditLogResponse> getAll() {
        return auditLogRepository.findAll().stream()
                .map(auditLogMapper::toResponse)
                .toList();
    }
}
