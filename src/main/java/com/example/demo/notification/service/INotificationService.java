package com.example.demo.notification.service;

import com.example.demo.notification.dto.SalonOcupadoDTO;
import com.example.demo.notification.dto.SmsRequestDTO;

public interface INotificationService {
    void notificarOcupacionSalon(SalonOcupadoDTO salonOcupadoDTO);
    void enviarSms(SmsRequestDTO smsRequestDTO);
}