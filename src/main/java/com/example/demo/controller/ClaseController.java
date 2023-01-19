package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.example.demo.entity.Clase;

import com.example.demo.service.IClaseService;
@CrossOrigin(origins = { "http://localhost:4200"})
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
			return new ResponseEntity<>(clases, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
	}
	
	@GetMapping("/clases/{id}")
	public ResponseEntity<Clase> findById(@PathVariable(name = "id")long id){
		
		Clase clase = claseService.findById(id);
		
		if(clase == null) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(clase, HttpStatus.OK);
		
	}
	
	@PostMapping("/clases")
	public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody Clase clase, BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos si hay errores, relacionados a la validacion
		if(result.hasErrors()) {
			
			List<String> errors = result.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		//En caso se que no hayan errores se procede a realizar el insert en la base de datos
		
		try {
			
			Clase claseFromDb = claseService.save(clase);
			
			response.put("clase", claseFromDb);
			response.put("mensaje", "clase creado de manera exitosa");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El clase NO ha sido creado de manera exitosa:" + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping("/clases/{id}")
	public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Clase clase, @PathVariable(name = "id")long id, BindingResult result){
		
		Map<String, Object> response = new HashMap<>();
		
		//Verificamos si hay errores, relacionados a la validacion
		if(result.hasErrors()) {
			
			List<String> errors = result.getAllErrors().stream()
					.map(DefaultMessageSourceResolvable::getDefaultMessage)
					.collect(Collectors.toList());
			
			response.put("errors", errors);
			
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		
		//En caso se que no hayan errores se procede a realizar el insert en la base de datos
		
		try {
			//Primero verificamos si se encuentra en la base de datos
			Clase claseDb = claseService.findById(id);
			
			//Si no existe se envia el estado not found
			if(claseDb == null) {
				response.put("mensaje", "El clase con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
			}
			
			//En caso de que el clase si exista en la base de datos se continua con la actualizacións
			clase.setId(id);
			Clase claseFromDb = claseService.save(clase);
			
			response.put("clase", claseFromDb);
			response.put("mensaje", "clase creado de manera exitosa");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El clase NO ha sido actualizado de manera exitosa:" + e.getMostSpecificCause());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@DeleteMapping("/clases/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id")long id){
	
		Map<String, Object> response = new HashMap<>();
		
		try {
			//Se consulta en la base de datos si existe el edificio
			Clase clase = claseService.findById(id);
			
			//Si no existe se envia el estado not found
			if(clase == null) {
				response.put("mensaje", "El clase con el id " + id + " no se encuentra en la base de datos");
				return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
			}
			
			//Si existe se procede a eliminar el edificio
			claseService.delete(id);
			
			response.put("mensaje", "clase eliminado de manera correcta");
			return new ResponseEntity<>(response, HttpStatus.OK);
			
			
		} catch (DataAccessException e) {
			response.put("Mensaje", "El clase NO ha sido eliminado de manera exitosa:" + e.getMostSpecificCause().toString());
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
	}

	@GetMapping("/clases/salon/{id}")
	public List<Clase> findBySalon(@PathVariable Long id){
		return claseService.findBySalon(id);
	}
	@GetMapping("/clases/salon/{id}/sort/horainicio")
	public List<Clase> findBySalonSort(@PathVariable Long id){
		System.out.println("Ordenado");
		System.out.println(claseService.findBySalon(id, Sort.by("horaInicio")));
		return claseService.findBySalon(id, Sort.by("horaInicio"));
	}

	@GetMapping("/clases/horasOcupadas/{idClase}/salon/{id}/dia/{dia}")
	public List<String> horasPorDia(@PathVariable Long id, @PathVariable Long idClase,@PathVariable String dia){
		return claseService.horasPorDia(id, idClase, dia);
	}

	@GetMapping("/clases/salon/{id}/orderByHoraInicio")
	public List<Clase> findBySalonOrderByHoraInicio(@PathVariable Long id){
		return claseService.findBySalonOrderByHoraInicio(id);
	}
}
