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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Clase;

import com.example.demo.service.IClaseService;

@RestController
@RequestMapping("/api")
public class ClaseController {

	@Autowired
	private IClaseService claseService;
	
	@GetMapping("/clases")
	public ResponseEntity<List<Clase>> findAll(){
		
		Sort sort = Sort.by("dia", "horaInicio");
		
		List<Clase> clases = claseService.findAll(sort);
		
		if(clases.size() > 0 ) {
			return new ResponseEntity<List<Clase>>(clases, HttpStatus.OK);
		}else {
			return new ResponseEntity<List<Clase>>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/clases/{id}")
	public ResponseEntity<Clase> findById(@PathVariable(name = "id", required = true)long id){
		
		Clase clase = claseService.findById(id);
		
		if(clase == null) {
			return new ResponseEntity<Clase>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<Clase>(clase, HttpStatus.OK);
		
	}
	
	@PostMapping("/clases")
	public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Clase clase, BindingResult result){
		
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
			
			Clase claseFromDb = claseService.save(clase);
			
			response.put("clase", claseFromDb);
			response.put("mensaje", "clase creado de manera exitosa");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El clase NO ha sido creado de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/clases/{id}")
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Clase clase, @PathVariable(name = "id", required = true)long id, BindingResult result){
		
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
			Clase claseDb = claseService.findById(id);
			
			//Si no existe se envia el estado not found
			if(claseDb == null) {
				response.put("mensaje", "El clase con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
			}
			
			//En caso de que el clase si exista en la base de datos se continua con la actualizaci??ns
			clase.setId(id);
			Clase claseFromDb = claseService.save(clase);
			
			response.put("clase", claseFromDb);
			response.put("mensaje", "clase creado de manera exitosa");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El clase NO ha sido actualizado de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/clases/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id", required = true)long id){
	
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Se consulta en la base de datos si existe el edificio
			Clase clase = claseService.findById(id);
			
			//Si no existe se envia el estado not found
			if(clase == null) {
				response.put("mensaje", "El clase con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
			}
			
			//Si existe se procede a eliminar el edificio
			claseService.delete(id);
			
			response.put("mensaje", "clase eliminado de manera correcta");
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK);
			
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El clase NO ha sido eliminado de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}
}
