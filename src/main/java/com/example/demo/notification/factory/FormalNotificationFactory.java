package com.example.demo.notification.factory;

import com.example.demo.notification.products.EmailNotifier;
import com.example.demo.notification.products.FormalEmailNotifier;
import com.example.demo.notification.products.FormalSMSNotifier;
import com.example.demo.notification.products.SMSNotifier;

public class FormalNotificationFactory implements NotificationFactory {
    @Override
    public EmailNotifier createEmailNotifier() {
        return new FormalEmailNotifier();
    }

    @Override
    public SMSNotifier createSMSNotifier() {
        return new FormalSMSNotifier();
    }
}
