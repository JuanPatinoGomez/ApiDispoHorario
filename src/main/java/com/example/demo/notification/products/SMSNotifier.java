package com.example.demo.notification.products;

public interface SMSNotifier {
    void sendSMS(String to, String message);
}
