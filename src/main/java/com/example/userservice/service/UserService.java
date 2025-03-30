package com.example.userservice.service;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.enums.Status;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.password.PasswordGenerator;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.updating.UpdateFields;
import com.example.userservice.validation.UserValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final UserValidator userValidator;
    private final PasswordGenerator passwordGenerator;
    private final UpdateFields updateFields;
    private final UserMapper userMapper;

    @Transactional
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {

        userValidator.validateNewUser(userRequestDTO);
        User user = userMapper.toEntity(userRequestDTO);
        prepareNewUser(user);

        User savedUser = userRepository.save(user);
        emailService.sendPasswordEmail(user.getEmail(), user.getPassword());

        return userMapper.toDto(savedUser);
    }

    public List<UserResponseDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Transactional
    public UserResponseDTO updateUser(UUID userId, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        updateFields.applyUpdates(existingUser, userRequestDTO);

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }
    @Transactional
    public void deleteUser(UUID userId) {
        if (!userRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(userId);
    }

    public void prepareNewUser(User user){
        String password = passwordGenerator.generate();
        user.setPassword(password);
        user.setRegistrationDate(java.util.Date.from(Instant.now()));
        user.setLastLogin(null);
        user.setStatus(Status.ACTIVE);

    }
}
