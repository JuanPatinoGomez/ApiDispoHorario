package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Sede;
import org.springframework.data.domain.Sort;

import com.example.demo.entity.Edificio;

public interface IEdificioService {
	
	List<Edificio> findAll(Sort sort);
	
	Edificio findById(long id);
	
	void delete(long id);
	
	Edificio save(Edificio edificio);

	List<Edificio> findAllBySede(Long idSede);

}
