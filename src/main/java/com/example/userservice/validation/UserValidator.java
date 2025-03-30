package com.example.userservice.validation;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


//UserValidator --- Проверяет, можно ли изменять логин/email.
@Component
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;

    public void validateNewUser(UserRequestDTO userRequestDTO) {
        validateLogin(userRequestDTO.getLogin());
        validateEmail(userRequestDTO.getEmail());
    }

    public void validateUpdateUser(UserRequestDTO newUser, User existingUser) {
        if (newUser.getLogin() != null && !newUser.getLogin().equals(existingUser.getLogin())) {
            validateLogin(newUser.getLogin());
        }
        if (newUser.getEmail() != null && !newUser.getEmail().equals(existingUser.getEmail())) {
            validateEmail(newUser.getEmail());
        }
    }

    private void validateLogin(String login) {
        if (userRepository.existsByLogin(login)) {
            throw new IllegalArgumentException("Логин уже используется");
        }
    }

    private void validateEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email уже зарегистрирован");
        }
    }
}
