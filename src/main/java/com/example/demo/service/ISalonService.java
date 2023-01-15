package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Edificio;
import org.springframework.data.domain.Sort;

import com.example.demo.entity.Salon;


public interface ISalonService {
	
	List<Salon> findAll(Sort sort);
	
	Salon findById(long id);
	
	void delete(long id);
	
	Salon save(Salon salon);

	List<Salon> findByEdificio(Long idEdificio);

	List<Salon> findByEdificioOrderByNumero(Long idEdificio);


}
