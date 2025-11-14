package com.example.demo.validation.asignacion;

import com.example.demo.repository.IClaseRepository;
import com.example.demo.usecase.AsignarClaseCommand;
import com.example.demo.validation.ValidationContext;
import com.example.demo.validation.core.AbstractValidationHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.example.demo.entity.Dia;

@Component
@Scope("prototype")
public class SalonDisponibleEnHorarioHandler extends AbstractValidationHandler<AsignarClaseCommand> {
    private final IClaseRepository claseRepository;

    public SalonDisponibleEnHorarioHandler(IClaseRepository claseRepository) {
        this.claseRepository = claseRepository;
    }

    @Override
    protected void doHandle(AsignarClaseCommand cmd, ValidationContext ctx) {
        if (cmd.salonId == null || cmd.dia == null || cmd.horaInicio == null || cmd.horaFinalizacion == null) return;
        Dia diaEnum;
        try {
            diaEnum = Dia.fromJson(cmd.dia);
        } catch (IllegalArgumentException ex) {
            ctx.addError("dia", "invalido", ex.getMessage());
            return;
        }
        // Consulta todas las clases del salón y día y calcula solapamiento inclusivo (bloquea pegadas)
        var existentes = claseRepository.findBySalonIdAndDia(cmd.salonId, diaEnum);
        
        for (var existente : existentes) {
            if (existente.getHoraInicio() == null || existente.getHoraFinalizacion() == null) continue;
            
            boolean overlapInclusivo = !cmd.horaInicio.isAfter(existente.getHoraFinalizacion())
                    && !cmd.horaFinalizacion.isBefore(existente.getHoraInicio());
            if (overlapInclusivo) {
                ctx.addError("horario", "ocupado", "El salón está ocupado en ese horario");
                break;
            }
        }
    }
}
