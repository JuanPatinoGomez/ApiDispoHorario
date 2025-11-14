package com.example.demo.validation.salon;

import com.example.demo.entity.Salon;
import com.example.demo.repository.ISalonRepository;
import com.example.demo.validation.ValidationContext;
import com.example.demo.validation.core.AbstractValidationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class NumeroUnicoPorEdificioHandler extends AbstractValidationHandler<Salon> {

    private final ISalonRepository salonRepository;

    @Autowired
    public NumeroUnicoPorEdificioHandler(ISalonRepository salonRepository) {
        this.salonRepository = salonRepository;
    }

    @Override
    protected void doHandle(Salon salon, ValidationContext ctx) {
        if (salon.getEdificio() == null || salon.getEdificio().getId() == null) return;
        int numero = salon.getNumero();
        Long edificioId = salon.getEdificio().getId();

        boolean exists = (salon.getId() == null)
                ? salonRepository.existsByNumeroAndEdificioId(numero, edificioId)
                : salonRepository.existsByNumeroAndEdificioIdAndIdNot(numero, edificioId, salon.getId());

        if (exists) {
            ctx.addError("numero", "duplicado", "Ya existe un salón con ese número en el edificio");
        }
    }
}
