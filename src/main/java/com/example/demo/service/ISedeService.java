package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Sede;

public interface ISedeService {
	
	public List<Sede> findAll();
	
	public Sede findById(long id);
	
	public void delete(long id);
	
	public Sede save(Sede sede);

}
