package com.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class EmailConfig {
    @Bean
    public JavaMailSender getJavaMailSender()
    {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);


        Properties props = mailSender.getJavaMailProperties();
        //props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.connectiontimeout", 50000);
        props.put("mail.smtp.timeout", 30000);

        return mailSender;
    }

//    @Bean
//    public SimpleMailMessage emailTemplate(String email)
//    {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setFrom("admin@gmail.com");
//        message.setText("FATAL - Application crash. Save your job !!");
//        return message;
//    }
}
