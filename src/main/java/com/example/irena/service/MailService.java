package com.example.irena.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MailService {
    @Qualifier("javasampleapproachMailSender")
    private final JavaMailSender javaMailSender;
    public boolean sendMail(String sendingEmail, String massage, String code) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("qweqq@gmail.com");
            message.setTo(sendingEmail);
            message.setSubject(massage);
            message.setText(code);
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
