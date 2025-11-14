package com.example.demo.validation.core;

import com.example.demo.validation.ValidationContext;

public abstract class AbstractValidationHandler<T> implements ValidationHandler<T> {
    private ValidationHandler<T> next;

    @Override
    public ValidationHandler<T> setNext(ValidationHandler<T> next) {
        this.next = next;
        return next;
    }

    @Override
    public void handle(T target, ValidationContext ctx) {
        doHandle(target, ctx);
        if (!ctx.isFailFast() || !ctx.hasErrors()) {
            if (next != null) {
                next.handle(target, ctx);
            }
        }
    }

    protected abstract void doHandle(T target, ValidationContext ctx);
}
