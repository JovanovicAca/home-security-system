package com.app.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailServiceImpl {

    @Autowired
    private JavaMailSender mailSender;


    final String username = "bezbednost27@gmail.com";
    final String password = "Bezbednost27!";

    public void sendMail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
        System.out.println("Email sent...");
    }
}
//    public void sendMessage(String to, String subject, String text) throws MessagingException {
////        Properties prop = new Properties();
////        prop.setProperty("mail.smtp.host", "smtp.gmail.com");
////        prop.put("mail.smtp.port", "587");
////        prop.put("mail.smtp.auth", "true");
////        prop.put("mail.smtp.socketFactory.port", "587");
////        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
//
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(25);
//        mailSender.setUsername(username);
//        mailSender.setPassword(password);Properties props = mailSender.getJavaMailProperties();
//
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        Session session = Session.getInstance(prop,
//                new javax.mail.Authenticator() {
//                    protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                    }
//                });
//        try{
//            Message message = new MimeMessage(session);
//            message.setFrom(new InternetAddress(username));
//            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
//            message.setSubject("subject");
//            message.setText("text");
//            Transport.send(message);
//            System.out.println("Email sent...");
//        }catch (MessagingException e) {
//            e.printStackTrace();
//        }
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setFrom("bezbednost27@gmail.com");
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        mailSender.send(message);
//
//
//    }
//}
