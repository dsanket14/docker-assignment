package com.nagarro.nagp.service;

import com.nagarro.nagp.exception.GenericException;
import com.nagarro.nagp.model.Order;
import com.nagarro.nagp.repo.OrderRepository;
import com.nagarro.nagp.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class NotificationService {

    private final JavaMailSender emailSender;
    @Autowired
    public NotificationService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendNotification(String recipientEmail, String subject, String message,String from) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(recipientEmail);
        mailMessage.setFrom(from);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        emailSender.send(mailMessage);
    }
}

