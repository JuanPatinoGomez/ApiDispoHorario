package com.example.demo.notification.products;


import com.example.demo.configuration.NotificationConfig;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class FormalSMSNotifier implements SMSNotifier {

    private static boolean twilioInitialized = false;

    @Override
    public void sendSMS(String to, String message) {
        if (!twilioInitialized) {
            Twilio.init(
                NotificationConfig.TWILIO_ACCOUNT_SID,
                NotificationConfig.TWILIO_AUTH_TOKEN
            );
            twilioInitialized = true;
        }
    
        String formalMessage = "Estimado usuario,\n" + message + "\nSaludos cordiales.";
        Message.creator(
                new PhoneNumber(to),
                new PhoneNumber(NotificationConfig.TWILIO_PHONE_NUMBER),
                formalMessage).create();
        System.out.println("[Formal SMS] Enviado a " + to);
    }
}
