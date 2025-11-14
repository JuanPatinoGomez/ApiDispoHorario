package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.IEdificioRepository;
import com.example.demo.repository.ISalonRepository;
import com.example.demo.entity.Edificio;
import com.example.demo.entity.Salon;
import com.example.demo.validation.BusinessValidationException;
import com.example.demo.validation.ValidationContext;
import com.example.demo.validation.core.ValidationHandler;
import com.example.demo.validation.salon.SalonValidationChainFactory;

@Service
public class SalonServiceImpl implements ISalonService{
	
	@Autowired
	private ISalonRepository salonRepository;
	
	@Autowired
	private IEdificioRepository edificioRepository;

    @Autowired
    private SalonValidationChainFactory salonValidationChainFactory;

	@Override
	@Transactional(readOnly = true)
	public List<Salon> findAll(Sort sort) {
		return salonRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Salon findById(long id) {
		return salonRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		salonRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Salon save(Salon salon) {
		
		Edificio edificio = edificioRepository.findById(salon.getEdificio().getId()).orElse(null);
		
		salon.setEdificio(edificio);

        // Ejecutar cadena de validación de negocio para salón
        ValidationContext ctx = new ValidationContext(false);
        ValidationHandler<Salon> chain = salonValidationChainFactory.build();
        chain.handle(salon, ctx);
        if (ctx.hasErrors()) {
            throw new BusinessValidationException(ctx.getErrors());
        }

		return salonRepository.save(salon);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Salon> findByEdificio(Long idEdificio) {
		return salonRepository.findByEdificio(edificioRepository.findById(idEdificio).orElse(null));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Salon> findByEdificioOrderByNumero(Long idEdificio) {
		return salonRepository.findByEdificioOrderByNumero(edificioRepository.findById(idEdificio).orElse(null));
	}

}
