package com.example.demo.service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

		LocalTime horaFinalizacion = clase.getHoraInicio().plusHours(1);
		horaFinalizacion = horaFinalizacion.plusMinutes(30);

		clase.setHoraFinalizacion(horaFinalizacion);
		clase.setSalon(salon);
		
		return claseDao.save(clase);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Clase> findBySalon(Long idSalon) {
		return claseDao.findBySalon(salonDao.findById(idSalon).orElse(null));
	}

	@Override
	public List<Clase> findBySalon(Long idSalon, Sort sort) {
		return claseDao.findBySalon(salonDao.findById(idSalon).orElse(null), sort);
	}

	@Override
	public List<String> horasPorDia(Long idSalon, String dia){
		List<String> allHoras = Arrays.asList("06:00", "07:30", "09:00", "10:30", "12:00", "13:30", "15:00", "16:30", "18:00", "19:30", "21:00", "22:30");
		List<Clase> allClasesSalon = claseDao.findBySalon(salonDao.findById(idSalon).orElse(null));
		List<String> horasOcupadas = allClasesSalon.stream().filter(clase -> clase.getDia().equals(dia)).map(dd -> dd.getHoraInicio().toString()).collect(Collectors.toList());
		List<String> horasDisponibles = allHoras.stream().filter(alh -> !horasOcupadas.stream().anyMatch(hoo -> hoo.equals(alh))).collect(Collectors.toList());
		System.out.println("idsalon: " + idSalon + " / dia: " + dia);
		System.out.println(horasOcupadas);
		System.out.println(horasDisponibles);
		return horasDisponibles;
	}

}
