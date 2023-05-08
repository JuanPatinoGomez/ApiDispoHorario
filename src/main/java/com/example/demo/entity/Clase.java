package com.example.demo.entity;

import java.time.LocalTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "clases")
@JsonIgnoreProperties(ignoreUnknown = true, value = 
{"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Clase {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "El campo asignatura no puede ir vacio")
	@Size(min = 1, max = 25, message = "El campo nombre debe tener entre 1 y 25 caracteres")
	@Column(name = "nombre_asignatura")
	private String nombreAsignatura;
	
	@NotBlank(message = "El campo dia no puede ir vacio")
	private String dia;
	
	@Column(name = "hora_inicio")
	private LocalTime horaInicio;
	
	@Column(name = "hora_finalizacion")
	private LocalTime horaFinalizacion;
	
	@NotNull(message = "El campo salon no puede ir vacio")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "salon_id")
	@JsonIgnoreProperties(ignoreUnknown = true, value = {"edificio"})
	private Salon salon;

	public Clase() {
	}

	public Clase(Long id, String nombreAsignatura, String dia, LocalTime horaInicio, LocalTime horaFinalizacion, Salon salon) {
		this.id = id;
		this.nombreAsignatura = nombreAsignatura;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFinalizacion = horaFinalizacion;
		this.salon = salon;
	}

	public Clase(String nombreAsignatura, String dia, LocalTime horaInicio, LocalTime horaFinalizacion, Salon salon) {
		this.nombreAsignatura = nombreAsignatura;
		this.dia = dia;
		this.horaInicio = horaInicio;
		this.horaFinalizacion = horaFinalizacion;
		this.salon = salon;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreAsignatura() {
		return nombreAsignatura;
	}

	public void setNombreAsignatura(String nombreAsignatura) {
		this.nombreAsignatura = nombreAsignatura;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	public LocalTime getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(LocalTime horaInicio) {
		this.horaInicio = horaInicio;
	}

	public LocalTime getHoraFinalizacion() {
		return horaFinalizacion;
	}

	public void setHoraFinalizacion(LocalTime horaFinalizacion) {
		this.horaFinalizacion = horaFinalizacion;
	}

	public Salon getSalon() {
		return salon;
	}

	public void setSalon(Salon salon) {
		this.salon = salon;
	}
	
	
	

}
