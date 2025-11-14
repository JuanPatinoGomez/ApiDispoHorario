package com.example.demo.validation.asignacion;

import com.example.demo.usecase.AsignarClaseCommand;
import com.example.demo.validation.ValidationContext;
import com.example.demo.validation.core.AbstractValidationHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class RangoHorarioValidoHandler extends AbstractValidationHandler<AsignarClaseCommand> {
    @Override
    protected void doHandle(AsignarClaseCommand cmd, ValidationContext ctx) {
        if (cmd.horaInicio == null || cmd.horaFinalizacion == null) {
            ctx.addError("horario", "requerido", "Las horas de inicio y finalización son obligatorias");
            return;
        }
        if (!cmd.horaFinalizacion.isAfter(cmd.horaInicio)) {
            ctx.addError("horario", "rango_invalido", "La hora de finalización debe ser posterior a la de inicio");
        }
    }
}
