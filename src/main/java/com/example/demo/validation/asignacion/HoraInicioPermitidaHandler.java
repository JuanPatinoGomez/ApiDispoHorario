package com.example.demo.validation.asignacion;

import com.example.demo.usecase.AsignarClaseCommand;
import com.example.demo.validation.ValidationContext;
import com.example.demo.validation.core.AbstractValidationHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Scope("prototype")
public class HoraInicioPermitidaHandler extends AbstractValidationHandler<AsignarClaseCommand> {

    private final LocalTime min;
    private final LocalTime max;

    public HoraInicioPermitidaHandler(
            @Value("${validation.clases.hora-inicio-min:06:00}") String minStr,
            @Value("${validation.clases.hora-inicio-max:20:15}") String maxStr) {
        this.min = LocalTime.parse(minStr);
        this.max = LocalTime.parse(maxStr);
    }

    @Override
    protected void doHandle(AsignarClaseCommand cmd, ValidationContext ctx) {
        if (cmd.horaInicio == null) {
            ctx.addError("horaInicio", "requerido", "La hora de inicio es obligatoria");
            return;
        }
        LocalTime start = cmd.horaInicio;
        if (start.isBefore(min) || start.isAfter(max)) {
            ctx.addError("horaInicio", "fuera_de_rango",
                    String.format("La clase debe iniciar entre %s y %s", min, max));
        }

        if (cmd.horaFinalizacion == null) {
            // RangoHorarioValidoHandler ya marcará que falta fin, no detenemos la cadena aquí
            return;
        }
        LocalTime end = cmd.horaFinalizacion;
        if (end.isBefore(min) || end.isAfter(max)) {
            ctx.addError("horaFinalizacion", "fuera_de_rango",
                    String.format("La clase debe finalizar entre %s y %s", min, max));
        }
    }
}
