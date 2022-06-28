package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dao.IClaseDao;
import com.example.demo.dao.ISalonDao;
import com.example.demo.entity.Clase;
import com.example.demo.entity.Salon;

@Service
public class ClaseServiceImpl implements IClaseService{
	
	@Autowired
	private IClaseDao claseDao;
	
	@Autowired
	private ISalonDao salonDao;

	@Override
	@Transactional(readOnly = true)
	public List<Clase> findAll(Sort sort) {
		return claseDao.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Clase findById(long id) {
		return claseDao.findById(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		claseDao.deleteById(id);
	}

	@Override
	@Transactional
	public Clase save(Clase clase) {
		
		Salon salon = salonDao.findById(clase.getSalon().getId()).orElse(null);

		clase.setSalon(salon);
		
		return claseDao.save(clase);
	}

}
