package org.example.springdemo4.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginRequest(

        @NotBlank(message = "username can't be blank")
        String username,

        @NotBlank(message = "password can't be blank")
        String password
) {
}