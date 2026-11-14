package org.example.springdemo4.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.springdemo4.dto.request.AddRoleRequest;
import org.example.springdemo4.dto.request.CreateUserRequest;
import org.example.springdemo4.dto.response.UserResponse;
import org.example.springdemo4.entity.RoleEntity;
import org.example.springdemo4.entity.UserEntity;
import org.example.springdemo4.exception.AlreadyExistsException;
import org.example.springdemo4.exception.NotFoundException;
import org.example.springdemo4.mapper.UserMapper;
import org.example.springdemo4.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Transactional
    public UserResponse createUser(CreateUserRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            log.error("Username already exists: {}", request.username());
            throw new AlreadyExistsException("User with username '" + request.username() + "' already exists");
        }

        UserEntity user = UserEntity.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .build();
        user.addRole(RoleEntity.builder().name("ROLE_USER").build());

        UserEntity saved = userRepository.save(user);
        log.info("User created: {}", saved.getUsername());
        return userMapper.toResponse(saved);
    }

    @Transactional
    public UserResponse addRole(Long userId, AddRoleRequest request) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found. User ID: {}", userId);
                    return new NotFoundException("User not found: " + userId);
                });

        String roleName = request.role();
        boolean alreadyHas = user.getRoles().stream().anyMatch(r -> r.getName().equals(roleName));
        if (alreadyHas) {
            throw new AlreadyExistsException("User '" + user.getUsername() + "' already has role " + roleName);
        }

        user.addRole(RoleEntity.builder().name(roleName).build());
        UserEntity saved = userRepository.save(user);
        log.info("Role {} added to user {}", roleName, saved.getUsername());
        return userMapper.toResponse(saved);
    }
}