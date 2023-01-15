package com.example.demo.dao;

import java.util.List;

import com.example.demo.entity.Sede;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.entity.Edificio;

public interface IEdificioDao extends JpaRepository<Edificio, Long>{

	@Query(value = "select p from Edificio p left join fetch p.sede")
	public List<Edificio> findAll(Sort sort);
	
	@Query(value = "select p from Edificio p left join fetch p.sede where p.id = :id")
	public Edificio findById(long id);

	//@Query(value = "select p from Edificio p left join fetch p.sede where p.id = :id")
	List<Edificio> findAllBySede(Sede sede);

	List<Edificio> findAllBySedeOrderByNombre(Sede sede);
}
