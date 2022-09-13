package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Sede;
import org.springframework.data.domain.Sort;

import com.example.demo.entity.Edificio;

public interface IEdificioService {
	
	public List<Edificio> findAll(Sort sort);
	
	public Edificio findById(long id);
	
	public void delete(long id);
	
	public Edificio save(Edificio edificio);

	public List<Edificio> findAllBySede(Long idSede);

}
