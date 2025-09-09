package com.example.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    private static final Logger log = LoggerFactory.getLogger(MailService.class);
    private final JavaMailSender mailSender;
    private final String from;
    private final boolean mailEnabled;

    public MailService(JavaMailSender mailSender,
                       @Value("${app.mail.from:no-reply@example.com}") String from,
                       @Value("${spring.mail.host:}") String mailHost) {
        this.mailSender = mailSender;
        this.from = from;
        this.mailEnabled = (mailHost != null && !mailHost.isBlank());
    }

    public void sendOtp(String to, String otp) {
        if (!mailEnabled) {
            log.info("[DEV] OTP for {} is {}", to, otp);
            return;
        }
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(to);
        msg.setFrom(from);
        msg.setSubject("Your OTP Code");
        msg.setText("Your OTP code is: " + otp + "\nIt expires in 10 minutes.");
        mailSender.send(msg);
    }
}
