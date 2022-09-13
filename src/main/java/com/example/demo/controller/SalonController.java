package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Salon;
import com.example.demo.service.ISalonService;
@CrossOrigin(origins = { "http://localhost:4200"})
@RestController
@RequestMapping("/api")
public class SalonController {
	
	@Autowired
	private ISalonService salonService;
	
	@GetMapping("/salones")
	public ResponseEntity<List<Salon>> findAll(){
		
		Sort sort = Sort.by("numero");
		
		List<Salon> salones = salonService.findAll(sort);
		
		if(salones.size() > 0 ) {
			return new ResponseEntity<List<Salon>>(salones, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Salon>>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/salones/{id}")
	public ResponseEntity<Salon> findById(@PathVariable(name = "id", required = true)long id){
		
		Salon salon = salonService.findById(id);
		
		if(salon == null) {
			return new ResponseEntity<Salon>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Salon>(salon, HttpStatus.OK);
		
	}
	
	@PostMapping("/salones")
	public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Salon salon, BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos si hay errores, relacionados a la validacion
		if(result.hasErrors()) {
			
			List<String> errors = result.getAllErrors().stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		//En caso se que no hayan errores se procede a realizar el insert en la base de datos
		
		try {
			
			Salon salonFromDb = salonService.save(salon);
			
			response.put("salon", salonFromDb);
			response.put("mensaje", "Salon creado de manera exitosa");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El salon NO ha sido creado de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/salones/{id}")
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Salon salon, @PathVariable(name = "id", required = true)long id, BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos si hay errores, relacionados a la validacion
		if(result.hasErrors()) {
			
			List<String> errors = result.getAllErrors().stream()
					.map(error -> error.getDefaultMessage())
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
		}
		
		//En caso se que no hayan errores se procede a realizar el insert en la base de datos
		
		try {
			//Primero verificamos si se encuentra en la base de datos
			Salon salonDb = salonService.findById(id);
			
			//Si no existe se envia el estado not found
			if(salonDb == null) {
				response.put("mensaje", "El salon con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
			}
			
			//En caso de que el salon si exista en la base de datos se continua con la actualizacións
			salon.setId(id);
			Salon salonFromDb = salonService.save(salon);
			
			response.put("salon", salonFromDb);
			response.put("mensaje", "Salon creado de manera exitosa");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El salon NO ha sido actualizado de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/salones/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id", required = true)long id){
	
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Se consulta en la base de datos si existe el edificio
			Salon salon = salonService.findById(id);
			
			//Si no existe se envia el estado not found
			if(salon == null) {
				response.put("mensaje", "El salon con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
			}
			
			//Si existe se procede a eliminar el edificio
			salonService.delete(id);
			
			response.put("mensaje", "Salon eliminado de manera correcta");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El salon NO ha sido eliminado de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

	@GetMapping("/salones/edificio/{id}")
	public List<Salon> findByEdificio(@PathVariable Long id){
		return salonService.findByEdificio(id);
	}

}
