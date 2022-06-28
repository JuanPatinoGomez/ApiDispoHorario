package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Sede;
import com.example.demo.service.ISedeService;

@RestController
@RequestMapping("/api")
public class SedeController {
	
	@Autowired
	private ISedeService sedeService;
	
	@GetMapping("/sedes")
	public ResponseEntity<List<Sede>> findAll(){
		
		List<Sede> sedes = sedeService.findAll();
		
		if(sedes.size() > 0) {
			return new ResponseEntity<List<Sede>>(sedes, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/sedes/{id}")
	public ResponseEntity<Sede> findById(@PathVariable(name = "id", required = true)long id){
		
		Sede sede = sedeService.findById(id);
		
		if(sede == null) {
			return new ResponseEntity<Sede>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Sede>(sede, HttpStatus.OK);
	}
	
	@PostMapping("/sedes")
	public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Sede sede, BindingResult results){
		
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos si hay errores, relacionados a la validacion
		if(results.hasErrors()) {
			
			List<String> errors = results.getAllErrors().stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
			
		}
		
		//En caso se que no hayan errores se procede a realizar el insert en la base de datos
		try {
			Sede sedeFromDb = sedeService.save(sede);
			
			response.put("sede", sedeFromDb);
			response.put("mensaje", "Sede creada de manera exitosas");
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			
			response.put("Mensaje", "La sede NO ha sido creada de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/sedes/{id}")
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Sede sede, @PathVariable(name = "id", required = true)long id, BindingResult results){
		
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos si hay errores, relacionados a la validacion
		if(results.hasErrors()) {
			
			List<String> errors = results.getAllErrors().stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
			
		}
		
		//En caso se que no hayan errores se procede a realizar el insert en la base de datos
		try {
			
			//Se verifica si la sede existe en la base de datos
			Sede sededb = sedeService.findById(id);
			
			//Si no existe se envia el estado not found
			if(sededb == null) {
				response.put("mensaje", "La sede con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<Map<String,Object>>(HttpStatus.NOT_FOUND);
			}
			
			//En caso de que la sede si exista en la base de datos se continua con la actualización
			sede.setId(id);
			Sede sedeFromDb = sedeService.save(sede);
			
			response.put("sede", sedeFromDb);
			response.put("mensaje", "Sede actualizada de manera exitosas");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			
			response.put("Mensaje", "La sede NO ha sido actualizada de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/sedes/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id", required = true)long id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			//Se consulta en la base de datos si existe la sede
			Sede sede = sedeService.findById(id);
			
			//Si no existe se envia el estado not found
			if(sede == null) {
				response.put("mensaje", "La sede con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<Map<String,Object>>(HttpStatus.NOT_FOUND);
			}
			
			//Si existe se procede a eliminar la sede
			sedeService.delete(id);
			
			response.put("mensaje", "Sede eliminada de manera exitosas");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "La sede NO ha sido eliminada de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
