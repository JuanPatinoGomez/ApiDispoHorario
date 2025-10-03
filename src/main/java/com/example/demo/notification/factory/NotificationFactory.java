package com.example.demo.notification.factory;

import com.example.demo.notification.products.EmailNotifier;
import com.example.demo.notification.products.SMSNotifier;

public interface NotificationFactory {
    EmailNotifier createEmailNotifier();
    SMSNotifier createSMSNotifier();
}
