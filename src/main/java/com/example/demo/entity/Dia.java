package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.text.Normalizer;

public enum Dia {
    LUNES, MARTES, MIERCOLES, JUEVES, VIERNES, SABADO, DOMINGO;

    @JsonCreator
    public static Dia fromJson(String value) {
        if (value == null) return null;
        String normalized = Normalizer.normalize(value.trim(), Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toUpperCase();
        switch (normalized) {
            case "LUNES": return LUNES;
            case "MARTES": return MARTES;
            case "MIERCOLES": return MIERCOLES;
            case "JUEVES": return JUEVES;
            case "VIERNES": return VIERNES;
            case "SABADO": return SABADO;
            case "DOMINGO": return DOMINGO;
            default: throw new IllegalArgumentException("Día inválido: " + value);
        }
    }

    @JsonValue
    public String toJson() {
        String n = name().toLowerCase();
        return Character.toUpperCase(n.charAt(0)) + n.substring(1);
    }
}
