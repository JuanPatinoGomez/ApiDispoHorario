package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.ISedeRepository;
import com.example.demo.entity.Sede;

@Service
public class SedeServiceImpl implements ISedeService{
	
	@Autowired
	private ISedeRepository sedeRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Sede> findAll() {
		return sedeRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Sede findById(long id) {
		return sedeRepository.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(long id) {
		sedeRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Sede save(Sede sede) {
		return sedeRepository.save(sede);
	}

}
