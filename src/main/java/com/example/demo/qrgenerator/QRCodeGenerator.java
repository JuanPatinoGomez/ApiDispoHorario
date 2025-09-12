package com.example.demo.qrgenerator;

import com.example.demo.entity.Edificio;
import com.example.demo.entity.Salon;
import com.example.demo.entity.Sede;
import com.example.demo.qrgenerator.builder.LogoQRBuilder;
import com.example.demo.qrgenerator.builder.QRBuilder;
import com.example.demo.qrgenerator.builder.QRDirector;
import com.example.demo.qrgenerator.builder.StandardQRBuilder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;

public class QRCodeGenerator {



    private static QRCodeGenerator instancia;
    private final QRCodeWriter qrCodeWriter;
    private String urlApp;

    private QRCodeGenerator() {
        qrCodeWriter = new QRCodeWriter();
        try {
            urlApp = "http://" + obtenerIP() + ":4200/view";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static synchronized QRCodeGenerator getInstancia() {
        if (instancia == null) {
            instancia = new QRCodeGenerator();
        }
        return instancia;
    }





    public void generateQRCodeImage(String url, int width, int height, String filePath) throws WriterException, IOException {
        QRBuilder builder = new StandardQRBuilder();
        QRDirector director = new QRDirector(builder);
        director.construct(url, width, height, filePath, "qr", BarcodeFormat.QR_CODE);
    }

    public void generateQRCodeImageWithLogo(String url, int width, int height, String filePath, String logoPath) throws WriterException, IOException {
        LogoQRBuilder builder = new LogoQRBuilder();
        // Cargar el logo desde el archivo
        System.out.println("Logo path: " + logoPath);
        BufferedImage logo = ImageIO.read(new File(logoPath));
        builder.setLogo(logo);
        QRDirector director = new QRDirector(builder);
        director.construct(url, width, height, filePath, "qr_logo", BarcodeFormat.QR_CODE);
    }


    public void generateQRSalonImage(Long idSalon, int numeroSalon, String nombreEdificio, String sedeMunicipio, int width, int height, String filePath) throws WriterException, IOException {
        String url = urlApp + "/clases/salon/" + idSalon;
        String nombreimagen = sedeMunicipio + "_" + nombreEdificio + "_" + numeroSalon;
        QRBuilder builder = new StandardQRBuilder();
        QRDirector director = new QRDirector(builder);
        director.construct(url, width, height, filePath, nombreimagen, BarcodeFormat.QR_CODE);
    }


    public void generateQREdificioImage(HashMap<Long, Integer> salones, String nombreEdificio, String sedeMunicipio, int width, int height, String filePath) throws WriterException, IOException {
        for(HashMap.Entry<Long, Integer> entry : salones.entrySet()){
            String url = urlApp + "/clases/salon/" + entry.getKey();
            String nombreimagen = sedeMunicipio + "_" + nombreEdificio + "_" + entry.getValue();
            QRBuilder builder = new StandardQRBuilder();
            QRDirector director = new QRDirector(builder);
            director.construct(url, width, height, filePath, nombreimagen, BarcodeFormat.QR_CODE);
        }
    }


    public void generateQRSedeImage(Sede sede, int width, int height, String filePath) throws IOException, WriterException {
        recorrerEdificios(sede, width, height, filePath);
    }


    public void generateQRAllImage(List<Sede> sedes, int width, int height, String filePath) throws IOException, WriterException{
        for(Sede sede: sedes){
            recorrerEdificios(sede, width, height, filePath);
        }
    }


    private void recorrerEdificios(Sede sede, int width, int height, String filePath) throws WriterException, IOException {
        for(Edificio edificio: sede.getEdificios()){
            for(Salon salon : edificio.getSalones()){
                String url = urlApp + "/clases/salon/" + salon.getId();
                String nombreimagen = sede.getMunicipio() + "_" + edificio.getNombre() + "_" + salon.getNumero();
                QRBuilder builder = new StandardQRBuilder();
                QRDirector director = new QRDirector(builder);
                director.construct(url, width, height, filePath, nombreimagen, BarcodeFormat.QR_CODE);
            }
        }
    }

    private static String obtenerIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
}
