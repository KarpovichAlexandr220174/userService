package com.example.userservice.update;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.password.PasswordGenerator;
import com.example.userservice.model.User;
import com.example.userservice.service.email.EmailService;
import com.example.userservice.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//applyUpdates --- Изменяет объект User, устанавливает новые значения.
@Component
@RequiredArgsConstructor
public class UpdateFields {
    private final UserValidator userValidator;
    private final EmailService emailService;
    private final PasswordGenerator passwordGenerator;
    private final PasswordEncoder passwordEncoder;

    public void applyUpdates(User existingUser, UserRequestDTO newUserDTO) {
        userValidator.validateUpdateUser(newUserDTO, existingUser);

        if (newUserDTO.getLogin() != null && !newUserDTO.getLogin().equals(existingUser.getLogin())) {
            existingUser.setLogin(newUserDTO.getLogin());
        }

        if (newUserDTO.getEmail() != null && !newUserDTO.getEmail().equals(existingUser.getEmail())) {

            String newPassword = passwordGenerator.generate();
            String hashedPassword = passwordEncoder.encode(newPassword);

            existingUser.setEmail(newUserDTO.getEmail());

            existingUser.setPassword(hashedPassword);
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