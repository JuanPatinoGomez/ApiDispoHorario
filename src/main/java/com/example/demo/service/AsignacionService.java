package com.example.demo.service;

import com.example.demo.entity.Clase;
import com.example.demo.entity.Salon;
import com.example.demo.entity.Dia;
import com.example.demo.repository.IClaseRepository;
import com.example.demo.repository.ISalonRepository;
import com.example.demo.usecase.AsignarClaseCommand;
import com.example.demo.validation.BusinessValidationException;
import com.example.demo.validation.ValidationContext;
import com.example.demo.validation.asignacion.AsignacionValidationChainFactory;
import com.example.demo.validation.core.ValidationHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AsignacionService {

    private final AsignacionValidationChainFactory chainFactory;
    private final ISalonRepository salonRepository;
    private final IClaseRepository claseRepository;

    public AsignacionService(AsignacionValidationChainFactory chainFactory,
                             ISalonRepository salonRepository,
                             IClaseRepository claseRepository) {
        this.chainFactory = chainFactory;
        this.salonRepository = salonRepository;
        this.claseRepository = claseRepository;
    }

    @Transactional
    public Clase asignarClase(AsignarClaseCommand cmd) {
        // Ejecutar cadena de validación
        ValidationContext ctx = new ValidationContext(false);
        ValidationHandler<AsignarClaseCommand> chain = chainFactory.build();
        chain.handle(cmd, ctx);
        if (ctx.hasErrors()) {
            throw new BusinessValidationException(ctx.getErrors());
        }

        // Persistencia de la clase (asignación)
        Salon salon = salonRepository.findById(cmd.salonId).orElse(null);

        Clase clase = new Clase();
        clase.setNombreAsignatura(cmd.nombreAsignatura);
        clase.setDia(Dia.fromJson(cmd.dia));
        clase.setHoraInicio(cmd.horaInicio);
        clase.setHoraFinalizacion(cmd.horaFinalizacion);
        clase.setSalon(salon);
        clase.setCupo(cmd.cupo);

        return claseRepository.save(clase);
    }
}
