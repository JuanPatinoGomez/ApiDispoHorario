package com.example.demo.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "salones")
@JsonIgnoreProperties(ignoreUnknown = true, value = 
{"hibernateLazyInitializer", "handler", "fieldHandler"})
public class Salon {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "El campo mensaje no puede ir vacio")
	private String tipo;
	
	@Min(value = 0, message = "El campo numero debe ser mayor o igual a 0")
	private int numero;
	
	@NotNull(message = "El campo edificio no puede ir vacio")
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name = "edificio_id")
	@JsonIgnoreProperties(ignoreUnknown = true, value = {"sede"})
	private Edificio edificio;
	
	@OneToMany(mappedBy = "salon", cascade = CascadeType.REMOVE, orphanRemoval = true)
	@JsonIgnoreProperties(ignoreUnknown = true, value = {"salon"})
	private List<Clase> clases;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Edificio getEdificio() {
		return edificio;
	}

	public void setEdificio(Edificio edificio) {
		this.edificio = edificio;
	}
	
	
}
