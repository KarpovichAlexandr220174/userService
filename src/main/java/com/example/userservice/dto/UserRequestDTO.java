package com.example.userservice.dto;


import com.example.userservice.enums.Role;
import com.example.userservice.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Логин не может быть пустым")
    private String login;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Некорректный формат email")
    private String email;

    @NotBlank(message = "Пароль не может быть пустым")
    private String password;

    @NotNull(message = "Роль не может быть пустой")
    private Role role;

    @NotNull(message = "Статус не может быть пустым")
    private Status status;

}
