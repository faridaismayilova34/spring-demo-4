package org.example.springdemo4.dto.response;

import java.util.List;

public record UserResponse(Long id, String username, List<String> roles) {
}