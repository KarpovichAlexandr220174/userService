package com.example.userservice.service.email;

public interface EmailService {

    void sendPasswordEmail(String toEmail, String password);
}
