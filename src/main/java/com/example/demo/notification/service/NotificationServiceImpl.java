package com.example.demo.notification.service;

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

        // Cambia la fábrica para probar diferentes estilos
        NotificationFactory factory = new FormalNotificationFactory();
        EmailNotifier emailNotifier = factory.createEmailNotifier();
        SMSNotifier smsNotifier = factory.createSMSNotifier();

        String mensajeFormal = "Se solicito ocupar para el día " + salonOcupadoDTO.getDia() + " en el horario de " + salonOcupadoDTO.getHoraInicio() + " a " + salonOcupadoDTO.getHoraFin() + " en el salón " + salonOcupadoDTO.getNumeroSalon() + ".";
        String mensajeFormalSms = "Nueva solicitud para el salón " + salonOcupadoDTO.getNumeroSalon() + " el día " + salonOcupadoDTO.getDia();

        emailNotifier.sendEmail("juanillochocolisto@gmail.com", "Solicitud de Ocupación de Salón", mensajeFormal);
        smsNotifier.sendSMS("+573143759212", mensajeFormalSms);

        // Prueba con estilo informal
        factory = new InformalNotificationFactory();
        emailNotifier = factory.createEmailNotifier();
        smsNotifier = factory.createSMSNotifier();

        String mensajeInformal = "Tienes una nueva solicitud para el salón " + salonOcupadoDTO.getNumeroSalon() + " el día " + salonOcupadoDTO.getDia() + " de " + salonOcupadoDTO.getHoraInicio() + " a " + salonOcupadoDTO.getHoraFin() + ". ¡No olvides revisarla!";
        String mensajeInFormalSms = "Revisar la solicitud para el salón " + salonOcupadoDTO.getNumeroSalon() + " mirar el correo para más detalles.";

        emailNotifier.sendEmail("juanillochocolisto@gmail.com", "Revisar solicitud", mensajeInformal);
        smsNotifier.sendSMS("+573143759212", mensajeInFormalSms);

        System.out.println("===== Notificación de Ocupación de Salón =====");
        System.out.println("Salón: " + salonOcupadoDTO.getNumeroSalon());
        System.out.println("Día: " + salonOcupadoDTO.getDia());
        System.out.println("Horario: " + salonOcupadoDTO.getHoraInicio() + " - " + salonOcupadoDTO.getHoraFin());
        System.out.println("==============================================");
    }

    @Override
    public void enviarSms(SmsRequestDTO smsRequestDTO) {
        // Usamos la fábrica para crear el notificador de SMS.
        NotificationFactory factory = new FormalNotificationFactory();
        SMSNotifier smsNotifier = factory.createSMSNotifier();
        smsNotifier.sendSMS(smsRequestDTO.getTo(), smsRequestDTO.getMessage());
    }
}