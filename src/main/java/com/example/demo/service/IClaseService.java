package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Salon;
import org.springframework.data.domain.Sort;

import com.example.demo.entity.Clase;

public interface IClaseService {
	
	List<Clase> findAll(Sort sort);
	
	Clase findById(long id);
	
	void delete(long id);
	
	Clase save(Clase clase);

	List<Clase> findBySalon(Long idSalon);

	List<Clase> findBySalon(Long idSalon, Sort sort);

	List<String> horasPorDia(Long idSalon, Long idClase, String dia);

	List<Clase> findBySalonOrderByHoraInicio(Long idSalon);

}
