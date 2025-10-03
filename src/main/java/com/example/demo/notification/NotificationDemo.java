package com.example.demo.notification;

import com.example.demo.notification.factory.FormalNotificationFactory;
import com.example.demo.notification.factory.InformalNotificationFactory;
import com.example.demo.notification.factory.NotificationFactory;
import com.example.demo.notification.products.EmailNotifier;
import com.example.demo.notification.products.SMSNotifier;

public class NotificationDemo {
    public static void main(String[] args) {
        // Cambia la fábrica para probar diferentes estilos
        NotificationFactory factory = new FormalNotificationFactory();
        EmailNotifier emailNotifier = factory.createEmailNotifier();
        SMSNotifier smsNotifier = factory.createSMSNotifier();

        emailNotifier.sendEmail("juanillochocolisto@gmail.com", "Asunto de prueba", "Este es un mensaje de prueba.");
        smsNotifier.sendSMS("+573001234567", "Este es un SMS de prueba.");

        // Prueba con estilo informal
        factory = new InformalNotificationFactory();
        emailNotifier = factory.createEmailNotifier();
        smsNotifier = factory.createSMSNotifier();

        emailNotifier.sendEmail("juanillochocolisto@gmail.com", "Asunto informal", "Mensaje informal de prueba.");
        smsNotifier.sendSMS("+573001234567", "Mensaje informal de prueba.");
    }
}
