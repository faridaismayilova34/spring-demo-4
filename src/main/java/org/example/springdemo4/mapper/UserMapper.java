package org.example.springdemo4.mapper;

import org.example.springdemo4.dto.response.UserResponse;
import org.example.springdemo4.entity.RoleEntity;
import org.example.springdemo4.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(UserEntity entity) {
        return new UserResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getRoles().stream().map(RoleEntity::getName).toList());
    }
}