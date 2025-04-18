package com.example.userservice.password;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class PasswordGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int PASSWORD_LENGTH = 8;
    private final SecureRandom random = new SecureRandom();

    public String generate(){
        StringBuilder password = new StringBuilder(PASSWORD_LENGTH);
        for(int i = 0; i < PASSWORD_LENGTH; i++){
            password.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return password.toString();
    }

}
