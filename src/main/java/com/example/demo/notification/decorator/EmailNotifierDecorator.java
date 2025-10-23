package com.example.demo.notification.decorator;

import com.example.demo.notification.products.EmailNotifier;

public abstract class EmailNotifierDecorator implements EmailNotifier {
    protected final EmailNotifier wrappee;

    protected EmailNotifierDecorator(EmailNotifier wrappee) {
        this.wrappee = wrappee;
    }

    @Override
    public void sendEmail(String to, String subject, String body) {
        // delega al componente envuelto
        wrappee.sendEmail(to, subject, body);
    }
}