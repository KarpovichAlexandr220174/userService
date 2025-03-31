package com.example.userservice.dto;

import com.example.userservice.enums.Role;
import com.example.userservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserResponseDTO {

    private UUID id;
    private String login;
    private String email;
    private Role role;
    private Status status;
    private Instant registrationDate;
    private Instant lastLogin;
    private boolean passwordResetRequired;

}
