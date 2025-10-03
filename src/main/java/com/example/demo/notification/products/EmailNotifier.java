package com.example.demo.notification.products;

public interface EmailNotifier {
    void sendEmail(String to, String subject, String message);
}
