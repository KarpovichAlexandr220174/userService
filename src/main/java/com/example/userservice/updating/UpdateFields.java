package com.example.userservice.updating;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.password.PasswordGenerator;
import com.example.userservice.model.User;
import com.example.userservice.service.EmailService;
import com.example.userservice.validation.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

//applyUpdates --- Изменяет объект User, устанавливает новые значения.
@Component
@RequiredArgsConstructor
public class UpdateFields {
    private final UserValidator userValidator;
    private final EmailService emailService;
    private final PasswordGenerator passwordGenerator;

    public void applyUpdates(User existingUser, UserRequestDTO newUserDTO) {
        userValidator.validateUpdateUser(newUserDTO, existingUser);

        if (newUserDTO.getLogin() != null && !newUserDTO.getLogin().equals(existingUser.getLogin())) {
            existingUser.setLogin(newUserDTO.getLogin());
        }

        if (newUserDTO.getEmail() != null && !newUserDTO.getEmail().equals(existingUser.getEmail())) {
            existingUser.setEmail(newUserDTO.getEmail());
            String newPassword = passwordGenerator.generate();
            existingUser.setPassword(newPassword);
            emailService.sendPasswordEmail(newUserDTO.getEmail(), newPassword);
        }

        if (newUserDTO.getRole() != null) {
            existingUser.setRole(newUserDTO.getRole());
        }

        if (newUserDTO.getStatus() != null) {
            existingUser.setStatus(newUserDTO.getStatus());
        }

    }
}