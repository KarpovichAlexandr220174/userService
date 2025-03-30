package com.example.userservice.service.user;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;
import com.example.userservice.enums.Status;
import com.example.userservice.exceptions.UserNotFoundException;
import com.example.userservice.mapper.UserMapper;
import com.example.userservice.password.PasswordGenerator;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import com.example.userservice.service.email.EmailServiceImpl;
import com.example.userservice.update.UpdateFields;
import com.example.userservice.validate.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final EmailServiceImpl emailService;
    private final UserValidator userValidator;
    private final PasswordGenerator passwordGenerator;
    private final UpdateFields updateFields;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {

        userValidator.validateNewUser(userRequestDTO);
        User user = userMapper.toEntity(userRequestDTO);
        prepareNewUser(user);

        User savedUser = userRepository.save(user);
        emailService.sendPasswordEmail(user.getEmail(), user.getPassword());

        return userMapper.toDto(savedUser);
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UUID userId, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(userId));

        updateFields.applyUpdates(existingUser, userRequestDTO);

        User updatedUser = userRepository.save(existingUser);

        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        userRepository.delete(user);
    }

    private void prepareNewUser(User user){
        String password = passwordGenerator.generate();
        user.setPassword(password);
        user.setRegistrationDate(Instant.now());
        user.setLastLogin(null);
        user.setStatus(Status.ACTIVE);

    }
}
