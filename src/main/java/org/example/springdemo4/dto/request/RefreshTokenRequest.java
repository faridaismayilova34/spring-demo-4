package org.example.springdemo4.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequest(

        @NotBlank(message = "refreshToken can't be blank")
        String refreshToken
) {
}