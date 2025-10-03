package com.example.demo.notification.factory;

import com.example.demo.notification.products.EmailNotifier;
import com.example.demo.notification.products.InformalEmailNotifier;
import com.example.demo.notification.products.InformalSMSNotifier;
import com.example.demo.notification.products.SMSNotifier;

public class InformalNotificationFactory implements NotificationFactory {
    @Override
    public EmailNotifier createEmailNotifier() {
        return new InformalEmailNotifier();
    }

    @Override
    public SMSNotifier createSMSNotifier() {
        return new InformalSMSNotifier();
    }
}
