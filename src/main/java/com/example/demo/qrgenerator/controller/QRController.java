package com.example.demo.qrgenerator.controller;

import com.example.demo.qrgenerator.service.IQRService;
import com.google.zxing.WriterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController()
@RequestMapping("/qr")
public class QRController {

    @Autowired
    private IQRService qrService;

    @GetMapping("/app")
    public ResponseEntity<String> generarCodigoQRApp(){
        try {
            qrService.qrApp("/aplicacion");
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            return new ResponseEntity<>("No se pudo generar la imagen del codigo qr", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/salon/{id}")
    public ResponseEntity<String> generarCodigoQRSalon(@PathVariable Long id){
        try {
            qrService.salonQr(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            return new ResponseEntity<>("No se pudo generar la imagen del codigo qr", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/edificio/{id}")
    public ResponseEntity<String> generarCodigoQREdificio(@PathVariable Long id){
        try {
            qrService.edificioQrs(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            return new ResponseEntity<>("No se pudo generar las imagenes de los codigos qr", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/sede/{id}")
    public ResponseEntity<String> generarCodigoQRSede(@PathVariable Long id){
        try {
            qrService.sedeQrs(id);
            return new ResponseEntity<>("Ok", HttpStatus.OK);
        } catch (WriterException | IOException e) {
            //throw new RuntimeException(e);
            return new ResponseEntity<>("No se pudo generar las imagenes de los codigos qr", HttpStatus.INTERNAL_SERVER_ERROR);
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
