package com.example.demo.repository;

import java.util.List;

import com.example.demo.entity.Salon;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Clase;

public interface IClaseRepository extends JpaRepository<Clase, Long>{

	@Query(value = "select p from Clase p left join fetch p.salon")
	List<Clase> findAll(Sort sort);
	
	@Query(value = "select p from Clase p left join fetch p.salon where p.id = :id")
	Clase findById(long id);

	List<Clase> findBySalon(Salon salon);

	List<Clase> findBySalon(Salon salon, Sort sort);
	List<Clase> findBySalonOrderByHoraInicio(Salon salon);
}
