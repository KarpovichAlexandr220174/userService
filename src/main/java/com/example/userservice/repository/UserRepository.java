package com.example.userservice.repository;

import com.example.userservice.enums.Role;
import com.example.userservice.enums.Status;
import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<User> findByLogin(String login);
    boolean existsByLogin(String login);
    List<User> findByRole(Role role);
    List<User> findByStatus(Status status);
}
