package com.example.demo.validation.core;

import com.example.demo.validation.ValidationContext;

public interface ValidationHandler<T> {
    ValidationHandler<T> setNext(ValidationHandler<T> next);
    void handle(T target, ValidationContext ctx);
}
