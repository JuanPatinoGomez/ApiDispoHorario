package com.example.demo.validation.asignacion;

import com.example.demo.usecase.AsignarClaseCommand;
import com.example.demo.validation.core.ValidationHandler;
import com.example.demo.validation.decorator.LoggingHandlerDecorator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AsignacionValidationChainFactory {
    private final ObjectProvider<HoraInicioPermitidaHandler> horaInicioProv;
    private final ObjectProvider<RangoHorarioValidoHandler> rangoProv;
    private final ObjectProvider<SalonActivoHandler> salonActivoProv;
    private final ObjectProvider<SalonDisponibleEnHorarioHandler> disponibilidadProv;
    private final ObjectProvider<CapacidadSuficienteHandler> capacidadProv;
    private final boolean log;

    public AsignacionValidationChainFactory(
            ObjectProvider<HoraInicioPermitidaHandler> horaInicioProv,
            ObjectProvider<RangoHorarioValidoHandler> rangoProv,
            ObjectProvider<SalonActivoHandler> salonActivoProv,
            ObjectProvider<SalonDisponibleEnHorarioHandler> disponibilidadProv,
            ObjectProvider<CapacidadSuficienteHandler> capacidadProv,
            @Value("${validation.logging:true}") boolean log) {
        this.horaInicioProv = horaInicioProv;
        this.rangoProv = rangoProv;
        this.salonActivoProv = salonActivoProv;
        this.disponibilidadProv = disponibilidadProv;
        this.capacidadProv = capacidadProv;
        this.log = log;
    }

    public ValidationHandler<AsignarClaseCommand> build() {
        ValidationHandler<AsignarClaseCommand> h0 = horaInicioProv.getObject();
        ValidationHandler<AsignarClaseCommand> h1 = rangoProv.getObject();
        ValidationHandler<AsignarClaseCommand> h2 = salonActivoProv.getObject();
        ValidationHandler<AsignarClaseCommand> h3 = disponibilidadProv.getObject();
        ValidationHandler<AsignarClaseCommand> h4 = capacidadProv.getObject();

        if (log) {
            h0 = new LoggingHandlerDecorator<>(h0);
            h1 = new LoggingHandlerDecorator<>(h1);
            h2 = new LoggingHandlerDecorator<>(h2);
            h3 = new LoggingHandlerDecorator<>(h3);
            h4 = new LoggingHandlerDecorator<>(h4);
        }

        h0.setNext(h1).setNext(h2).setNext(h3).setNext(h4);
        return h0;
    }
}
