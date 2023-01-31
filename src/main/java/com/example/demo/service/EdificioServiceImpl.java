package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.IEdificioRepository;
import com.example.demo.repository.ISedeRepository;
import com.example.demo.entity.Edificio;
import com.example.demo.entity.Sede;

@Service
public class EdificioServiceImpl implements IEdificioService{
	
	@Autowired
	private IEdificioRepository edificioRepository;
	
	@Autowired
	private ISedeRepository sedeRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Edificio> findAll(Sort sort) {
		return edificioRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Edificio findById(long id) {
		return edificioRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		edificioRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Edificio save(Edificio edificio) {
		
		Sede sede = sedeRepository.findById(edificio.getSede().getId()).orElse(null);
		
		edificio.setSede(sede);
		
		return edificioRepository.save(edificio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Edificio> findAllBySede(Long idSede) {

		return edificioRepository.findAllBySede(sedeRepository.findById(idSede).orElse(null));
	}

	@Override
	public List<Edificio> findAllBySedeOrderByNombre(Long idSede) {
		return edificioRepository.findAllBySedeOrderByNombre(sedeRepository.findById(idSede).orElse(null));
	}

}
