package com.example.demo.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {
    
    @Value("${user.default.gmail}")
    private String userDefaultGmail;

    @Value("${pass.default.gmail}")
    private String passDefaultGmail;

    @Value("${twilio.account.sid}")
    private String twilioAccountSid;

    @Value("${twilio.auth.token}")
    private String twilioAuthToken;

    @Value("${twilio.phone.number}")
    private String twilioPhoneNumber;

    public static String USER_DEFAULT_GMAIL;
    public static String PASS_DEFAULT_GMAIL;
    public static String TWILIO_ACCOUNT_SID;
    public static String TWILIO_AUTH_TOKEN;
    public static String TWILIO_PHONE_NUMBER;

    @PostConstruct
    public void init() {
        USER_DEFAULT_GMAIL = userDefaultGmail;
        PASS_DEFAULT_GMAIL = passDefaultGmail;
        TWILIO_ACCOUNT_SID = twilioAccountSid;
        TWILIO_AUTH_TOKEN = twilioAuthToken;
        TWILIO_PHONE_NUMBER = twilioPhoneNumber;
    }


    public String getUserDefaultGmail() {
        return userDefaultGmail;
    }

    public String getPassDefaultGmail() {
        return passDefaultGmail;
    }

    public String getTwilioAccountSid() {
        return twilioAccountSid;
    }

    public String getTwilioAuthToken() {
        return twilioAuthToken;
    }

    public String getTwilioPhoneNumber() {
        return twilioPhoneNumber;
    }
}
