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
public class SalonActivoHandler extends AbstractValidationHandler<AsignarClaseCommand> {
    private final ISalonRepository salonRepository;

    public SalonActivoHandler(ISalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    @Override
    protected void doHandle(AsignarClaseCommand cmd, ValidationContext ctx) {
        if (cmd.salonId == null) {
            ctx.addError("salonId", "requerido", "El salón es obligatorio");
            return;
        }
        Salon salon = salonRepository.findById(cmd.salonId).orElse(null);
        if (salon == null) {
            ctx.addError("salonId", "no_encontrado", "El salón no existe");
            return;
        }
        if (!salon.isActivo()) {
            ctx.addError("salon", "inactivo", "El salón está inactivo");
        }
    }
}
