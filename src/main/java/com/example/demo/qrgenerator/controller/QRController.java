package com.example.demo.qrgenerator.controller;

import com.example.demo.qrgenerator.service.IQRService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = { "http://localhost:4200"})
@RestController()
@RequestMapping("/qr")
public class QRController {

    @Autowired
    private IQRService qrService;

    @GetMapping("/app")
    public ResponseEntity<Map<String, String>> generarCodigoQRApp(){
        Map<String, String> response = new HashMap<>();
        try {
            //qrService.qrApp("/aplicacion");
            qrService.qrApp("http://localhost:4200/");
            response.put("mensaje","Se generaron las imagenes con los códigos qr de manera correcta");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            response.put("mensaje","No se pudo generar las imagenes de los códigos qr");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/salon/{id}")
    public ResponseEntity<Map<String, String>> generarCodigoQRSalon(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try {
            qrService.salonQr(id);
            response.put("mensaje","Se genero la imagen con el código qr de manera correcta");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            response.put("mensaje","No se pudo generar las imagenes de los códigos qr");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/edificio/{id}")
    public ResponseEntity<Map<String, String>> generarCodigoQREdificio(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try {
            qrService.edificioQrs(id);
            response.put("mensaje","Se generaron las imagenes con los códigos qr de manera correcta");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            response.put("mensaje","No se pudo generar las imagenes de los códigos qr");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sede/{id}")
    public ResponseEntity<Map<String, String>> generarCodigoQRSede(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try {
            qrService.sedeQrs(id);
            response.put("mensaje","Se generaron las imagenes con los códigos qr de manera correcta");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            response.put("mensaje","No se pudo generar las imagenes de los códigos qr");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<String> generarCodigoQRAll(){
        try {
            qrService.allQrs();
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            return new ResponseEntity<>("No se pudo generar las imagenes de los codigos qr", HttpStatus.OK);
        }
    }
}
