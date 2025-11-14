package com.example.demo.validation.decorator;

import com.example.demo.validation.ValidationContext;
import com.example.demo.validation.core.ValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingHandlerDecorator<T> implements ValidationHandler<T> {
    private static final Logger log = LoggerFactory.getLogger(LoggingHandlerDecorator.class);
    private final ValidationHandler<T> delegate;

    public LoggingHandlerDecorator(ValidationHandler<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public ValidationHandler<T> setNext(ValidationHandler<T> next) {
        // Delegate linking to the wrapped handler and return the result to preserve
        // the chaining semantics used by the callers (h0.setNext(h1).setNext(h2)).
        return delegate.setNext(next);
    }

    @Override
    public void handle(T target, ValidationContext ctx) {
        long t0 = System.currentTimeMillis();
        log.debug(">> {}", delegate.getClass().getSimpleName());
        delegate.handle(target, ctx);
        log.debug("<< {} ({} ms)", delegate.getClass().getSimpleName(), System.currentTimeMillis() - t0);
    }
}
