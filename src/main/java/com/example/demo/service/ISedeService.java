package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Sede;

public interface ISedeService {
	
	List<Sede> findAll();
	
	Sede findById(long id);
	
	void delete(long id);
	
	Sede save(Sede sede);

}
