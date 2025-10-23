package com.example.demo.notification.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.notification.products.SMSNotifier;

public class LoggingSmsDecorator extends SmsNotifierDecorator {
    private static final Logger log = LoggerFactory.getLogger(LoggingSmsDecorator.class);

    public LoggingSmsDecorator(SMSNotifier wrappee) {
        super(wrappee);
    }

    @Override
    public void sendSMS(String phoneNumber, String message) {
        log.info("SMS -> to={}", phoneNumber);
        super.sendSMS(phoneNumber, message); // delega al wrappee
        log.info("SMS sent -> to={}", phoneNumber);
    }
}