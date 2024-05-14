package com.example.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Component
public class EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    @Async
    public void sendEmail(String to, String subject, String body) {
        // Настройки подключения к почтовому серверу
        String host = "smtp.yandex.ru";
        int port = 465;
        String username = "Mihalkevitc.23V@yandex.ru";
        String password = "jrqetdxmplyjqyhj";
        String recipient = to;
        Properties props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);}});
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            System.out.println("Письмо успешно отправлено.");
            logger.info("Email sent successfully to: {}", to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
