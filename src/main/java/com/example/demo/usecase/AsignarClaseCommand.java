package com.example.demo.usecase;

import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonFormat;

public class AsignarClaseCommand {
    public Long salonId;
    public String nombreAsignatura;
    public String dia; // e.g., "LUNES", "MARTES"
    @JsonFormat(pattern = "HH:mm[:ss]")
    public LocalTime horaInicio;
    @JsonFormat(pattern = "HH:mm[:ss]")
    public LocalTime horaFinalizacion;
    public int cupo;
}
