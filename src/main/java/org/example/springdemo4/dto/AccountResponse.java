package org.example.springdemo4.dto;

import java.math.BigDecimal;

public record AccountResponse(Long id, String ownerName, BigDecimal balance) {
}