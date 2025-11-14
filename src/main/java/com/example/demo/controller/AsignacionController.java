package com.example.demo.controller;

import com.example.demo.entity.Clase;
import com.example.demo.service.AsignacionService;
import com.example.demo.usecase.AsignarClaseCommand;
import com.example.demo.validation.BusinessValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/asignaciones")
public class AsignacionController {

    private final AsignacionService asignacionService;

    public AsignacionController(AsignacionService asignacionService) {
        this.asignacionService = asignacionService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> asignar(@RequestBody AsignarClaseCommand cmd) {
        Map<String, Object> resp = new HashMap<>();
        try {
            Clase clase = asignacionService.asignarClase(cmd);
            resp.put("clase", clase);
            resp.put("mensaje", "Asignación creada correctamente");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (BusinessValidationException e) {
            resp.put("errors", e.getErrors().stream().map(err -> err.getMessage()).collect(Collectors.toList()));
            return new ResponseEntity<>(resp, HttpStatus.BAD_REQUEST);
        }
    }
}
