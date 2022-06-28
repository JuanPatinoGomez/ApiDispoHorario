package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.example.demo.entity.Clase;

public interface IClaseService {
	
	public List<Clase> findAll(Sort sort);
	
	public Clase findById(long id);
	
	public void delete(long id);
	
	public Clase save(Clase clase);

}
