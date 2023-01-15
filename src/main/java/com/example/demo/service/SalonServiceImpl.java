package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IEdificioDao;
import com.example.demo.dao.ISalonDao;
import com.example.demo.entity.Edificio;
import com.example.demo.entity.Salon;

@Service
public class SalonServiceImpl implements ISalonService{
	
	@Autowired
	private ISalonDao salonDao;
	
	@Autowired
	private IEdificioDao edificioDao;

	@Override
	@Transactional(readOnly = true)
	public List<Salon> findAll(Sort sort) {
		return salonDao.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Salon findById(long id) {
		return salonDao.findById(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		salonDao.deleteById(id);
	}

	@Override
	@Transactional
	public Salon save(Salon salon) {
		
		Edificio edificio = edificioDao.findById(salon.getEdificio().getId()).orElse(null);
		
		salon.setEdificio(edificio);

		return salonDao.save(salon);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Salon> findByEdificio(Long idEdificio) {
		return salonDao.findByEdificio(edificioDao.findById(idEdificio).orElse(null));
	}

	@Override
	@Transactional(readOnly = true)
	public List<Salon> findByEdificioOrderByNumero(Long idEdificio) {
		return salonDao.findByEdificioOrderByNumero(edificioDao.findById(idEdificio).orElse(null));
	}

}
