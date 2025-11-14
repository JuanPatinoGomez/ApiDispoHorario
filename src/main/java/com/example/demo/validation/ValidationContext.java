package com.example.demo.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationContext {
    private final boolean failFast;
    private final List<ValidationError> errors = new ArrayList<>();

    public ValidationContext() { this(false); }

    public ValidationContext(boolean failFast) { this.failFast = failFast; }

    public boolean isFailFast() { return failFast; }

    public void addError(String field, String code, String message) {
        errors.add(new ValidationError(field, code, message));
    }

    public boolean hasErrors() { return !errors.isEmpty(); }

    public List<ValidationError> getErrors() { return errors; }
}
