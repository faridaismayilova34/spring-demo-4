package org.example.springdemo4.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.springdemo4.dto.request.AddRoleRequest;
import org.example.springdemo4.dto.request.CreateUserRequest;
import org.example.springdemo4.dto.response.UserResponse;
import org.example.springdemo4.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@Valid @RequestBody CreateUserRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/{id}/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public UserResponse addRole(@PathVariable Long id, @Valid @RequestBody AddRoleRequest request) {
        return userService.addRole(id, request);
    }
}