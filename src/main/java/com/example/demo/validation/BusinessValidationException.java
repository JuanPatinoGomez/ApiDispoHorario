package com.example.demo.validation;

import java.util.List;

public class BusinessValidationException extends RuntimeException {
    private final List<ValidationError> errors;

    public BusinessValidationException(List<ValidationError> errors) {
        super("Errores de validación de negocio");
        this.errors = errors;
    }

    public List<ValidationError> getErrors() { return errors; }
}
