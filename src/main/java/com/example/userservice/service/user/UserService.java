package com.example.userservice.service.user;

import com.example.userservice.dto.UserRequestDTO;
import com.example.userservice.dto.UserResponseDTO;

import java.util.List;
import java.util.UUID;

public interface UserService {

    UserResponseDTO addUser(UserRequestDTO userRequestDTO);
    List<UserResponseDTO> getUsers();
    UserResponseDTO updateUser(UUID userId, UserRequestDTO userRequestDTO);
    void deleteUser(UUID userId);
}
