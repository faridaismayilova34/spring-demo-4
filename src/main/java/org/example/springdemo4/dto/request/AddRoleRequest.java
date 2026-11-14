package org.example.springdemo4.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddRoleRequest(

        @NotBlank(message = "role can't be blank")
        @Pattern(regexp = "ROLE_ADMIN|ROLE_USER", message = "role must be ROLE_ADMIN or ROLE_USER")
        String role
) {
}
