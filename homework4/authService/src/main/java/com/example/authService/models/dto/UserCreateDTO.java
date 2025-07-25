package com.example.authService.models.dto;

import com.example.authService.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserCreateDTO {
    private UUID id;
    private String login;
    private String password;
    private String email;
    private UserRole userRole;
}
