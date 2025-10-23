package com.example.demo.notification.decorator;

import com.example.demo.notification.products.SMSNotifier;

public abstract class SmsNotifierDecorator implements SMSNotifier {
    protected final SMSNotifier wrappee;

    protected SmsNotifierDecorator(SMSNotifier wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void sendSMS(String phoneNumber, String message) {
        wrappee.sendSMS(phoneNumber, message);
    }
}