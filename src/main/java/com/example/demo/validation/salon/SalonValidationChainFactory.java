package com.example.demo.validation.salon;

import com.example.demo.entity.Salon;
import com.example.demo.validation.core.ValidationHandler;
import com.example.demo.validation.decorator.LoggingHandlerDecorator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SalonValidationChainFactory {

    private final ObjectProvider<NumeroUnicoPorEdificioHandler> unicoProv;
    private final boolean log;

    public SalonValidationChainFactory(
            ObjectProvider<NumeroUnicoPorEdificioHandler> unicoProv,
            @Value("${validation.logging:false}") boolean log) {
        this.unicoProv = unicoProv;
        this.log = log;
    }

    public ValidationHandler<Salon> build() {
        ValidationHandler<Salon> h1 = unicoProv.getObject();
        if (log) {
            h1 = new LoggingHandlerDecorator<>(h1);
        }
        return h1;
    }
}
