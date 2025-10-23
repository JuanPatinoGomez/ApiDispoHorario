package com.example.demo.notification.decorator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.demo.notification.products.EmailNotifier;

public class LoggingEmailDecorator extends EmailNotifierDecorator {
    private static final Logger log = LoggerFactory.getLogger(LoggingEmailDecorator.class);

    public LoggingEmailDecorator(EmailNotifier wrappee) {
        super(wrappee);
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        log.info("EMAIL -> to={}, subject={}", to, subject);
        super.sendEmail(to, subject, body); // llama al wrappee a través del decorador base
        log.info("EMAIL sent -> to={}", to);
    }
}