package com.example.userservice.model;

import com.example.userservice.enums.Role;
import com.example.userservice.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false, unique = true)
    private UUID id;

    @NotBlank(message = "Login не может быть пустым")
    @Column(unique = true, nullable = false)
    private String login;

    @NotBlank(message = "Email не может быть пустым")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column(nullable = false, updatable = false)
    private Instant registrationDate;

    @Column(nullable = false, updatable = false)
    private Instant lastLogin;
}
