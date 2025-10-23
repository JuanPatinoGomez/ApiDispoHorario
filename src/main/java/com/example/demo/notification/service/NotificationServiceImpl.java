package com.example.demo.notification.service;

import com.example.demo.notification.decorator.LoggingEmailDecorator;
import com.example.demo.notification.decorator.LoggingSmsDecorator;
import com.example.demo.notification.dto.SalonOcupadoDTO;
import com.example.demo.notification.dto.SmsRequestDTO;
import com.example.demo.notification.factory.FormalNotificationFactory;
import com.example.demo.notification.factory.InformalNotificationFactory;
import com.example.demo.notification.factory.NotificationFactory;
import com.example.demo.notification.products.EmailNotifier;
import com.example.demo.notification.products.SMSNotifier;

import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements INotificationService {

    @Override
    public void notificarOcupacionSalon(SalonOcupadoDTO salonOcupadoDTO) {

        // obtener productos desde la Abstract Factory (no se modifica la fábrica)
        NotificationFactory factory = new FormalNotificationFactory();
        EmailNotifier emailNotifier = factory.createEmailNotifier();
        SMSNotifier smsNotifier = factory.createSMSNotifier();

        // envolver con decoradores (clásico: BaseDecorator -> ConcreteDecorator)
        emailNotifier = new LoggingEmailDecorator(emailNotifier);
        smsNotifier = new LoggingSmsDecorator(smsNotifier);

        String mensajeFormal = "Se solicitó ocupar para el día " + salonOcupadoDTO.getDia()
                + " en el horario de " + salonOcupadoDTO.getHoraInicio() + " a " + salonOcupadoDTO.getHoraFin()
                + " en el salón " + salonOcupadoDTO.getNumeroSalon() + ".";
        String mensajeFormalSms = "Nueva solicitud para el salón " + salonOcupadoDTO.getNumeroSalon()
                + " el día " + salonOcupadoDTO.getDia();

        emailNotifier.sendEmail("juanillochocolisto@gmail.com", "Solicitud de Ocupación de Salón", mensajeFormal);
        smsNotifier.sendSMS("+573143759212", mensajeFormalSms);

        // mismo flujo para la fábrica informal si se desea
        factory = new InformalNotificationFactory();
        emailNotifier = new LoggingEmailDecorator(factory.createEmailNotifier());
        smsNotifier = new LoggingSmsDecorator(factory.createSMSNotifier());

        String mensajeInformal = "Se generó una nueva solicitud para el salón " + salonOcupadoDTO.getNumeroSalon();
        String mensajeInFormalSms = "Revisar la solicitud para el salón " + salonOcupadoDTO.getNumeroSalon();

        emailNotifier.sendEmail(salonOcupadoDTO.getGmailSolicitante(), "Revisar solicitud", mensajeInformal);
        smsNotifier.sendSMS("+57" + salonOcupadoDTO.getNumeroSolicitante(), mensajeInFormalSms);
    }

    @Override
    public void enviarSms(SmsRequestDTO smsRequestDTO) {
        NotificationFactory factory = new FormalNotificationFactory();
        SMSNotifier smsNotifier = new LoggingSmsDecorator(factory.createSMSNotifier());
        smsNotifier.sendSMS(smsRequestDTO.getTo(), smsRequestDTO.getMessage());
    }
}