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

import com.example.demo.entity.Edificio;
import com.example.demo.service.IEdificioService;
//@CrossOrigin(origins = { "http://localhost:4200"})
@RestController
@RequestMapping("/api/edificios")
public class EdificioController {
	
	@Autowired
	private IEdificioService edificioService;
	
	@GetMapping()
	public ResponseEntity<List<Edificio>> findAll(){
		
		Sort sort = Sort.by("nombre");
		
		List<Edificio> edificios = edificioService.findAll(sort);
		
		if(edificios.size() > 0) {
			return new ResponseEntity<List<Edificio>>(edificios, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Edificio>>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Edificio> findById(@PathVariable(name = "id")long id){
		
		Edificio edificio = edificioService.findById(id);
		
		if(edificio == null) {
			
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(edificio, HttpStatus.OK);
		
	}
	
	@PostMapping()
	public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Edificio edificio, BindingResult results){
		
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
			
			Edificio edificioFromDb = edificioService.save(edificio);
			
			response.put("edificio", edificioFromDb);
			response.put("mensaje", "Edificio creado de manera exitosa");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			
			response.put("Mensaje", "El edificio NO ha sido creado de manera exitosa:" + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Edificio edificio, @PathVariable(name = "id")long id, BindingResult results){
		
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
			//Primero verificamos si se encuentra en la base de datos
			Edificio edificiodb = edificioService.findById(id);
			
			//Si no existe se envia el estado not found
			if(edificiodb == null) {
				response.put("mensaje", "edificio con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			//En caso de que el edificio si exista en la base de datos se continua con la actualizacións
			edificio.setId(id);
			Edificio edificioFromDb = edificioService.save(edificio);
			
			response.put("edificio", edificioFromDb);
			response.put("mensaje", "Edificio creado de manera exitosa");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			
			response.put("Mensaje", "El edificio NO ha sido creado de manera exitosa:" + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id")long id){
		
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Se consulta en la base de datos si existe el edificio
			Edificio edificio = edificioService.findById(id);
			
			//Si no existe se envia el estado not found
			if(edificio == null) {
				response.put("mensaje", "edificio con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			//Si existe se procede a eliminar el edificio
			edificioService.delete(id);
			
			response.put("mensaje", "Edificio eliminado de manera correcta");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El edificio NO ha sido eliminado de manera exitosa:" + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		

	}

	@GetMapping(path = "/sede/{id}")
	public List<Edificio> findAllBySede(@PathVariable Long id){
		return edificioService.findAllBySede(id);
	}
	@GetMapping(path = "/sede/{id}/orderByNombre")
	public List<Edificio> findAllBySedeOrderByNombre(@PathVariable Long id){
		return edificioService.findAllBySedeOrderByNombre(id);
	}

}
