package com.example.demo.notification.products;

import javax.mail.*;
import javax.mail.internet.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.configuration.NotificationConfig;

import java.util.Properties;

public class FormalEmailNotifier implements EmailNotifier {



    @Override
    public void sendEmail(String to, String subject, String message) {
        // Configuración básica para SMTP (ejemplo con Gmail)
        final String username = NotificationConfig.USER_DEFAULT_GMAIL;
        final String password = NotificationConfig.PASS_DEFAULT_GMAIL;
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(username));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            msg.setSubject("[Formal] " + subject);
            msg.setText("Estimado usuario,\n\n" + message + "\n\nSaludos cordiales.");
            Transport.send(msg);
            System.out.println("Correo formal enviado a " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
