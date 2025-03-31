package com.example.userservice.service.email;

import com.example.userservice.exceptions.EmailSendingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService{

    @Value("${spring.mail.username}")
    private String sender;

    private final JavaMailSender mailSender;

    @Override
    public void sendPasswordEmail(String toEmail, String password) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("Ваш новый пароль");
            message.setText("Ваш пароль: " + password);
            message.setFrom(sender);

            mailSender.send(message);
        }catch (MailException e){
            throw new EmailSendingException("Ошибка при отправке письма на " + toEmail, e);
        }
    }
}
