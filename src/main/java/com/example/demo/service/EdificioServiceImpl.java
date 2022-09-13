package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IEdificioDao;
import com.example.demo.dao.ISedeDao;
import com.example.demo.entity.Edificio;
import com.example.demo.entity.Sede;

@Service
public class EdificioServiceImpl implements IEdificioService{
	
	@Autowired
	private IEdificioDao edificioDao;
	
	@Autowired
	private ISedeDao sedeDao;

	@Override
	@Transactional(readOnly = true)
	public List<Edificio> findAll(Sort sort) {
		return edificioDao.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Edificio findById(long id) {
		return edificioDao.findById(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		edificioDao.deleteById(id);
	}

	@Override
	@Transactional
	public Edificio save(Edificio edificio) {
		
		Sede sede = sedeDao.findById(edificio.getSede().getId()).orElse(null);
		
		edificio.setSede(sede);
		
		return edificioDao.save(edificio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Edificio> findAllBySede(Long idSede) {

		return edificioDao.findAllBySede(sedeDao.findById(idSede).orElse(null));
	}

}
