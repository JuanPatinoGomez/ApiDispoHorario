package com.example.demo.qrgenerator.controller;

import com.example.demo.entity.dto.InformacionQRDTO;
import com.example.demo.qrgenerator.service.IQRService;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@CrossOrigin(origins = { "http://localhost:4200"})
@RestController()
@RequestMapping("/qr")
public class QRController {

    @Autowired
    private IQRService qrService;

    public void generarCodigoQR(HttpServletResponse response, BitMatrix bitMatrix){

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);

            BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);

            // Obtener el flujo de salida de la respuesta HTTP
            OutputStream outputStream = response.getOutputStream();

            // Configurar el tipo de contenido y la cabecera de descarga
            response.setStatus(HttpStatus.OK.value());
            response.setContentType(MediaType.IMAGE_PNG_VALUE);

            // Escribir la imagen en el flujo de salida
            ImageIO.write(image, "png", outputStream);

            // Cerrar el flujo de salida
            outputStream.close();
        } catch (IOException e) {
            //throw new RuntimeException(e);

        }

    }

    public void generarZipCodigosQR(HttpServletResponse response, List<InformacionQRDTO> informacionQRDTOList){

        try {
            // Obtener el flujo de salida de la respuesta HTTP
            OutputStream outputStream = response.getOutputStream();

            // Configurar el tipo de contenido y la cabecera de descarga
            response.setContentType("application/zip");
            response.setHeader("Content-Disposition", "attachment; filename=images.zip");

            // Crear un nuevo archivo ZIP en el flujo de salida
            ZipOutputStream zipOutputStream = new ZipOutputStream(outputStream);

            // Generar y agregar las imágenes al archivo ZIP
            for (InformacionQRDTO informacionQRDTO : informacionQRDTOList) {
                String imageName = informacionQRDTO.getNombreArchivo() + ".png";
                BitMatrix bitMatrix = informacionQRDTO.getBitMatrix();
                BufferedImage image = MatrixToImageWriter.toBufferedImage(bitMatrix);
                addImageToZip(zipOutputStream, imageName, image);
            }

            // Cerrar el archivo ZIP
            zipOutputStream.close();

            // Cerrar el flujo de salida
            outputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);

        }

    }

    private void addImageToZip(ZipOutputStream zipOutputStream, String imageName, BufferedImage image) throws IOException {
        // Crear una nueva entrada en el archivo ZIP para la imagen
        ZipEntry zipEntry = new ZipEntry(imageName);
        zipOutputStream.putNextEntry(zipEntry);

        // Escribir la imagen en el archivo ZIP utilizando ImageIO
        ImageIO.write(image, "png", zipOutputStream);

        // Cerrar la entrada en el archivo ZIP
        zipOutputStream.closeEntry();
    }

    @GetMapping("/app")
    public void generarCodigoQRApp(HttpServletResponse response){

        try {
            BitMatrix bitMatrix = qrService.qrApp("http://localhost:4200/view/sedes");
            generarCodigoQR(response, bitMatrix);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);

        }

    }

    @GetMapping("/salon/{id}")
    public void generarCodigoQRSalon(@PathVariable Long id, HttpServletResponse response){

        try {
            BitMatrix bitMatrix = qrService.salonQr(id);
            generarCodigoQR(response, bitMatrix);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/edificio/{id}")
    public void generarCodigoQREdificio(@PathVariable Long id, HttpServletResponse response){
        try {
            List<InformacionQRDTO> informacionQRDTOList = qrService.edificioQrs(id);
            generarZipCodigosQR(response, informacionQRDTOList);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/sede/{id}")
    public void generarCodigoQRSede(@PathVariable Long id, HttpServletResponse response){
        try {
            List<InformacionQRDTO> informacionQRDTOList = qrService.sedeQrs(id);;
            generarZipCodigosQR(response, informacionQRDTOList);
        } catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/all")
    public void generarCodigoQRAll(HttpServletResponse response){
        try {
            List<InformacionQRDTO> informacionQRDTOList = qrService.allQrs();
            generarZipCodigosQR(response, informacionQRDTOList);
        }catch (WriterException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
