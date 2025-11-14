package com.example.demo.repository;

import java.util.List;
import java.time.LocalTime;

import com.example.demo.entity.Salon;
import com.example.demo.entity.Dia;
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

	// Verifica solapamiento de horario en un salón y día dados
	boolean existsBySalonIdAndDiaAndHoraInicioLessThanAndHoraFinalizacionGreaterThan(
			Long salonId,
			Dia dia,
			LocalTime fin,
			LocalTime inicio);

	// Variante inclusiva: también bloquea clases "pegadas" (touching)
	boolean existsBySalonIdAndDiaAndHoraInicioLessThanEqualAndHoraFinalizacionGreaterThanEqual(
			Long salonId,
			Dia dia,
			LocalTime fin,
			LocalTime inicio);

	// Listar todas las clases de un salón y día para cálculo manual de solapamiento
	@Query("select c from Clase c where c.salon.id = :salonId and c.dia = :dia")
	List<Clase> findBySalonIdAndDia(Long salonId, Dia dia);
}
