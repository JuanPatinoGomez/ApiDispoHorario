package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Edificio;
import org.springframework.data.domain.Sort;

import com.example.demo.entity.Salon;


public interface ISalonService {
	
	public List<Salon> findAll(Sort sort);
	
	public Salon findById(long id);
	
	public void delete(long id);
	
	public Salon save(Salon salon);

	public List<Salon> findByEdificio(Long idEdificio);

}
