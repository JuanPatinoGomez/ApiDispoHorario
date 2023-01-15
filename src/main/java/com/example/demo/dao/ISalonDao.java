package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Edificio;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Salon;

public interface ISalonDao extends JpaRepository<Salon, Long>{
	
	@Query(value = "select p from Salon p left join fetch p.edificio")
	List<Salon> findAll(Sort sort);
	
	@Query(value = "select p from Salon p left join fetch p.edificio where p.id = :id")
	Salon findById(long id);

	List<Salon> findByEdificio(Edificio edificio);

	List<Salon> findByEdificioOrderByNumero(Edificio edificio);
}
