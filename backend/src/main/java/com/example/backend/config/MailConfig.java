package com.example.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMessage.RecipientType;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class MailConfig {

    private static final Logger log = LoggerFactory.getLogger(MailConfig.class);

    /** Default: NO real emails are sent. */
    @Bean
    @Profile("!smtp")
    public JavaMailSender noOpMailSender() {
        return new JavaMailSenderImpl() {
            @Override public void send(SimpleMailMessage simpleMessage) {
                log.info("[NO-OP MAIL] to={}, subject={}",
                        (Object) simpleMessage.getTo(), simpleMessage.getSubject());
            }
            @Override public void send(SimpleMailMessage... simpleMessages) {
                if (simpleMessages != null) {
                    for (SimpleMailMessage m : simpleMessages) send(m);
                }
            }
            @Override public void send(MimeMessage mimeMessage) {
                try {
                    var to = mimeMessage.getRecipients(RecipientType.TO);
                    var subject = mimeMessage.getSubject();
                    log.info("[NO-OP MAIL] to={}, subject={}", to, subject);
                } catch (Exception e) {
                    log.info("[NO-OP MAIL] mime message logged");
                }
            }
            @Override public void send(MimeMessage... mimeMessages) {
                if (mimeMessages != null) {
                    for (MimeMessage m : mimeMessages) send(m);
                }
            }
            // optional: avoid network lookups
            @Override public MimeMessage createMimeMessage() { return super.createMimeMessage(); }
            @Override public MimeMessage createMimeMessage(InputStream contentStream) { return super.createMimeMessage(contentStream); }
            @Override public void setJavaMailProperties(Properties properties) { super.setJavaMailProperties(new Properties()); }
        };
    }
}
