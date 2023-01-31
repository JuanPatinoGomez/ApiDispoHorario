package com.example.demo.service;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.IClaseRepository;
import com.example.demo.repository.ISalonRepository;
import com.example.demo.entity.Clase;
import com.example.demo.entity.Salon;

@Service
public class ClaseServiceImpl implements IClaseService{
	
	@Autowired
	private IClaseRepository claseRepository;
	
	@Autowired
	private ISalonRepository salonRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Clase> findAll(Sort sort) {
		return claseRepository.findAll(sort);
	}

	@Override
	@Transactional(readOnly = true)
	public Clase findById(long id) {
		return claseRepository.findById(id);
	}

	@Override
	@Transactional
	public void delete(long id) {
		claseRepository.deleteById(id);
	}

	@Override
	@Transactional
	public Clase save(Clase clase) {
		
		Salon salon = salonRepository.findById(clase.getSalon().getId()).orElse(null);

		LocalTime horaFinalizacion = clase.getHoraInicio().plusHours(1);
		horaFinalizacion = horaFinalizacion.plusMinutes(30);

		clase.setHoraFinalizacion(horaFinalizacion);
		clase.setSalon(salon);
		
		return claseRepository.save(clase);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Clase> findBySalon(Long idSalon) {
		return claseRepository.findBySalon(salonRepository.findById(idSalon).orElse(null));
	}

	@Override
	public List<Clase> findBySalon(Long idSalon, Sort sort) {
		return claseRepository.findBySalon(salonRepository.findById(idSalon).orElse(null), sort);
	}

	@Override
	public List<String> horasPorDia(Long idSalon, Long idClase,String dia){
		List<String> allHoras = Arrays.asList("06:00:00", "07:30:00", "09:00:00", "10:30:00", "12:00:00", "13:30:00", "15:00:00", "16:30:00", "18:00:00", "19:30:00", "21:00:00", "22:30:00");
		List<Clase> allClasesSalon = claseRepository.findBySalon(salonRepository.findById(idSalon).orElse(null));
		List<String> horasOcupadas = allClasesSalon.stream().filter(clase -> clase.getDia().equals(dia) && (clase.getId() != idClase || idClase == 0L)).map(dd -> dd.getHoraInicio().toString().concat(":00")).collect(Collectors.toList());
		List<String> horasDisponibles = allHoras.stream().filter(alh -> !horasOcupadas.stream().anyMatch(hoo -> hoo.equals(alh))).collect(Collectors.toList());
		System.out.println("idsalon: " + idSalon + " / dia: " + dia);
		System.out.println(horasOcupadas);
		System.out.println(horasDisponibles);
		return horasDisponibles;
	}

	@Override
	public List<Clase> findBySalonOrderByHoraInicio(Long idSalon) {
		return claseRepository.findBySalonOrderByHoraInicio(salonRepository.findById(idSalon).orElse(null));
	}

}
