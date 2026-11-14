package org.example.springdemo4.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank(message = "username can't be blank")
        @Size(min = 3, max = 30, message = "username must be between 3 and 30 characters")
        @Pattern(regexp = "^[A-Za-z0-9._-]+$",
                message = "username may contain only letters, digits, '.', '_' and '-'")
        String username,

        @NotBlank(message = "password can't be blank")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^A-Za-z0-9])(?=\\S+$).{9,}$",
                message = "password must be at least 9 characters and contain an uppercase letter, "
                        + "a lowercase letter, a digit and a special symbol (no spaces)")
        String password
) {
}