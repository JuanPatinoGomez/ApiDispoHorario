package com.example.demo.qrgenerator;

import com.example.demo.entity.Edificio;
import com.example.demo.entity.Salon;
import com.example.demo.entity.Sede;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;

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
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }


    public void generateQRSalonImage(Long idSalon, int numeroSalon, String nombreEdificio, String sedeMunicipio, int width, int height, String filePath) throws WriterException, IOException {
        BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + idSalon, BarcodeFormat.QR_CODE, width, height);
        String nombreimagen = sedeMunicipio + "_" + nombreEdificio + "_" + numeroSalon;
        Path path = FileSystems.getDefault().getPath(filePath + nombreimagen + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("Se genero una imagen en la ruta: \n" + path);
    }


    public void generateQREdificioImage(HashMap<Long, Integer> salones, String nombreEdificio, String sedeMunicipio, int width, int height, String filePath) throws WriterException, IOException {
        for(HashMap.Entry<Long, Integer> entry : salones.entrySet()){
            BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + entry.getKey(), BarcodeFormat.QR_CODE, width, height);
            String nombreimagen = sedeMunicipio + "_" + nombreEdificio + "_" + entry.getValue();
            Path path = FileSystems.getDefault().getPath(filePath + nombreimagen + ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            System.out.println("Se genero una imagen en la ruta: \n" + path);
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
                BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + salon.getId(), BarcodeFormat.QR_CODE, width, height);
                String nombreimagen = sede.getMunicipio() + "_" + edificio.getNombre() + "_" + salon.getNumero();
                Path path = FileSystems.getDefault().getPath(filePath + nombreimagen + ".png");
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
                System.out.println("Se genero una imagen en la ruta: \n" + path);
            }
        }
    }

    private static String obtenerIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
}
