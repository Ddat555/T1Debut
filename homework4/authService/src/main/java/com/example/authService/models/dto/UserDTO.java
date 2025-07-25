package com.example.authService.models.dto;

import com.example.authService.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UserDTO {
    private UUID id;
    private String email;
    private UserRole userRole;
}
