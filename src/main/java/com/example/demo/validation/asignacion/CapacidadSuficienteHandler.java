package com.example.demo.validation.asignacion;

import com.example.demo.entity.Salon;
import com.example.demo.repository.ISalonRepository;
import com.example.demo.usecase.AsignarClaseCommand;
import com.example.demo.validation.ValidationContext;
import com.example.demo.validation.core.AbstractValidationHandler;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CapacidadSuficienteHandler extends AbstractValidationHandler<AsignarClaseCommand> {
    private final ISalonRepository salonRepository;

    public CapacidadSuficienteHandler(ISalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    @Override
    protected void doHandle(AsignarClaseCommand cmd, ValidationContext ctx) {
        if (cmd.salonId == null) return;
        Salon salon = salonRepository.findById(cmd.salonId).orElse(null);
        if (salon == null) return;
        if (cmd.cupo < 0) {
            ctx.addError("cupo", "valor_invalido", "El cupo no puede ser negativo");
            return;
        }
        if (salon.getCapacidad() > 0 && cmd.cupo > salon.getCapacidad()) {
            ctx.addError("capacidad", "insuficiente", "La capacidad del salón es menor al cupo de la clase");
        }
    }
}
