package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "sedes")
public class Sede {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "El campo municipio no debe ir vacio")
	@Size(min = 3, max = 30, message ="El campo municipio debe tener entre 3 y 30 caracteres")
	private String municipio;
	
	@NotBlank(message = "El campo direccion no debe ir vacio")
	@Size(min = 1, max = 255, message ="El campo direccion debe tener entre 1 y 255 caracteres")
	private String direccion;
	
	@OneToMany(mappedBy = "sede", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnoreProperties(ignoreUnknown = true, value = {"sede"})
	private List<Edificio> edificios;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMunicipio() {
		return municipio;
	}

	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	
}
