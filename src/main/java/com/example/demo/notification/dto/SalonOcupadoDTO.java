package com.example.demo.notification.dto;

import java.time.LocalTime;

public class SalonOcupadoDTO {
    private String numeroSalon;
    private String dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String gmailSolicitante;
    private String numeroSolicitante;

    public String getNumeroSalon() {
        return numeroSalon;
    }

    public void setNumeroSalon(String numeroSalon) {
        this.numeroSalon = numeroSalon;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getGmailSolicitante() {
        return gmailSolicitante;
    }

    public void setGmailSolicitante(String gmailSolicitante) {
        this.gmailSolicitante = gmailSolicitante;
    }

    public String getNumeroSolicitante() {
        return numeroSolicitante;
    }

    public void setNumeroSolicitante(String numeroSolicitante) {
        this.numeroSolicitante = numeroSolicitante;
    }
}