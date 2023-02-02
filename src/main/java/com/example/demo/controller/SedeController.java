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
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Sede;
import com.example.demo.service.ISedeService;
@CrossOrigin(origins = { "http://localhost:4200"})
@RestController
@RequestMapping("/api/sedes")
public class SedeController {
	
	@Autowired
	private ISedeService sedeService;
	
	@GetMapping()
	public ResponseEntity<List<Sede>> findAll(){
		
		List<Sede> sedes = sedeService.findAll();
		
		if(sedes.size() > 0) {
			return new ResponseEntity<>(sedes, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Sede> findById(@PathVariable(name = "id")long id){
		
		Sede sede = sedeService.findById(id);
		
		if(sede == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(sede, HttpStatus.OK);
	}
	
	@PostMapping()
	public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Sede sede, BindingResult results){
		
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos si hay errores, relacionados a la validacion
		if(results.hasErrors()) {
			
			List<String> errors = results.getAllErrors().stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			
		}
		
		//En caso se que no hayan errores se procede a realizar el insert en la base de datos
		try {
			Sede sedeFromDb = sedeService.save(sede);
			
			response.put("sede", sedeFromDb);
			response.put("mensaje", "Sede creada de manera exitosas");
			
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			
			response.put("Mensaje", "La sede NO ha sido creada de manera exitosa:" + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Sede sede, @PathVariable(name = "id")long id, BindingResult results){
		
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos si hay errores, relacionados a la validacion
		if(results.hasErrors()) {
			
			List<String> errors = results.getAllErrors().stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			
		}
		
		//En caso se que no hayan errores se procede a realizar el insert en la base de datos
		try {
			
			//Se verifica si la sede existe en la base de datos
			Sede sededb = sedeService.findById(id);
			
			//Si no existe se envia el estado not found
			if(sededb == null) {
				response.put("mensaje", "La sede con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			//En caso de que la sede si exista en la base de datos se continua con la actualización
			sede.setId(id);
			Sede sedeFromDb = sedeService.save(sede);
			
			response.put("sede", sedeFromDb);
			response.put("mensaje", "Sede actualizada de manera exitosas");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			
			response.put("Mensaje", "La sede NO ha sido actualizada de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id")long id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			
			//Se consulta en la base de datos si existe la sede
			Sede sede = sedeService.findById(id);
			
			//Si no existe se envia el estado not found
			if(sede == null) {
				response.put("mensaje", "La sede con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			//Si existe se procede a eliminar la sede
			sedeService.delete(id);
			
			response.put("mensaje", "Sede eliminada de manera exitosas");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "La sede NO ha sido eliminada de manera exitosa:" + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

}
