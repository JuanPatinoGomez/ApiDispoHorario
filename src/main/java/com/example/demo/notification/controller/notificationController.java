
package com.example.demo.notification.controller;

import com.example.demo.notification.dto.SalonOcupadoDTO;
import com.example.demo.notification.dto.SmsRequestDTO;
import com.example.demo.notification.products.FormalEmailNotifier;
import com.example.demo.notification.products.FormalSMSNotifier;
import com.example.demo.notification.products.InformalEmailNotifier;
import com.example.demo.notification.products.InformalSMSNotifier;
import com.example.demo.notification.service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class notificationController {

    @Autowired
    private INotificationService notificationService;

    @PostMapping("/ocupacion-salon")
    public ResponseEntity<String> notificarOcupacion(@RequestBody SalonOcupadoDTO salonOcupadoDTO) {
        notificationService.notificarOcupacionSalon(salonOcupadoDTO);
        return new ResponseEntity<>("Notificación recibida y procesada.", HttpStatus.OK);
    }

    @PostMapping("/enviar-sms")
    public ResponseEntity<String> enviarSms(@RequestBody SmsRequestDTO smsRequestDTO) {
        notificationService.enviarSms(smsRequestDTO);
        return new ResponseEntity<>("SMS enviado para procesamiento.", HttpStatus.OK);
    }

    @PostMapping("/direct-notification")
    public ResponseEntity<String> notificacionDirecta(@RequestBody SalonOcupadoDTO salonOcupadoDTO) {
        // Notificación directa, sin patrón Abstract Factory
        FormalEmailNotifier emailNotifier = new FormalEmailNotifier();
        FormalSMSNotifier smsNotifier = new FormalSMSNotifier();
        

        String mensajeFormal = "Se solicito ocupar para el día " + salonOcupadoDTO.getDia() + " en el horario de " + salonOcupadoDTO.getHoraInicio() + " a " + salonOcupadoDTO.getHoraFin() + " en el salón " + salonOcupadoDTO.getNumeroSalon() + ".";
        String mensajeFormalSms = "Nueva solicitud para el salón " + salonOcupadoDTO.getNumeroSalon() + " el día " + salonOcupadoDTO.getDia();

        

        emailNotifier.sendEmail("juanillochocolisto@gmail.com", "Ocupación de salón", mensajeFormal);
        smsNotifier.sendSMS("+573143759212", mensajeFormalSms);


        InformalEmailNotifier informalEmailNotifier = new InformalEmailNotifier();
        InformalSMSNotifier informalSMSNotifier = new InformalSMSNotifier();

        String mensajeInformal = "¡Hey! Recuerda que el salón " + salonOcupadoDTO.getNumeroSalon() + " está reservado para ti el " + salonOcupadoDTO.getDia() + " de " + salonOcupadoDTO.getHoraInicio() + " a " + salonOcupadoDTO.getHoraFin() + ". Nos vemos!";
        String mensajeInformalSms = "¡Hey! El salón " + salonOcupadoDTO.getNumeroSalon() + " es tuyo el " + salonOcupadoDTO.getDia() + ".";
        
        informalEmailNotifier.sendEmail(salonOcupadoDTO.getGmailSolicitante(), "Reserva salón", mensajeInformal);
        informalSMSNotifier.sendSMS("+57" + salonOcupadoDTO.getNumeroSolicitante(), mensajeInformalSms);

        return new ResponseEntity<>("Notificación directa enviada (formal e informal).", HttpStatus.OK);
    }
}
