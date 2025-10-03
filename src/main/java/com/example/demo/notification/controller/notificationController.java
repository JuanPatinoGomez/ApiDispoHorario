package com.example.demo.notification.controller;

import com.example.demo.notification.dto.SalonOcupadoDTO;
import com.example.demo.notification.dto.SmsRequestDTO;
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
}
