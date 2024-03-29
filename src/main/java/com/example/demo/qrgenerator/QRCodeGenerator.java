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

    //private static String urlApp = "http://localhost:8080/api";
    //private static String urlApp = "http://localhost:4200/view";
    private static String urlApp;

    static {
        try {
            urlApp = "http://" + obtenerIP() + ":4200/view";
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }


    public static void generateQRCodeImage(String url, int width, int height, String filePath) throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);

        Path path = FileSystems.getDefault().getPath(filePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
    }

    public static void generateQRSalonImage(Long idSalon, int numeroSalon, String nombreEdificio, String sedeMunicipio, int width, int height, String filePath) throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //Codigo qr con la url
        BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + idSalon, BarcodeFormat.QR_CODE, width, height);

        String nombreimagen = sedeMunicipio + "_" + nombreEdificio + "_" + numeroSalon;
        //Lugar en el que se genera la imagen
        Path path = FileSystems.getDefault().getPath(filePath + nombreimagen + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("Se genero una imagen en la ruta: \n" + path);
    }

    public static void generateQREdificioImage(HashMap<Long, Integer> salones, String nombreEdificio, String sedeMunicipio, int width, int height, String filePath) throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        for(HashMap.Entry<Long, Integer> entry : salones.entrySet()){
            //Codigo qr con la url
            BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + entry.getKey(), BarcodeFormat.QR_CODE, width, height);

            String nombreimagen = sedeMunicipio + "_" + nombreEdificio + "_" + entry.getValue();
            //Lugar en el que se genera la imagen
            Path path = FileSystems.getDefault().getPath(filePath + nombreimagen + ".png");
            MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
            System.out.println("Se genero una imagen en la ruta: \n" + path);
        }
    }

    public static void generateQRSedeImage(Sede sede, int width, int height, String filePath) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        recorrerEdificios(sede, width, height, filePath, qrCodeWriter);
    }

    public static void generateQRAllImage(List<Sede> sedes, int width, int height, String filePath) throws IOException, WriterException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        for(Sede sede: sedes){
            recorrerEdificios(sede, width, height, filePath, qrCodeWriter);
        }
    }

    private static void recorrerEdificios(Sede sede, int width, int height, String filePath, QRCodeWriter qrCodeWriter) throws WriterException, IOException {
        for(Edificio edificio: sede.getEdificios()){
            for(Salon salon : edificio.getSalones()){
                //Codigo qr con la url
                BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + salon.getId(), BarcodeFormat.QR_CODE, width, height);

                String nombreimagen = sede.getMunicipio() + "_" + edificio.getNombre() + "_" + salon.getNumero();
                //Lugar en el que se genera la imagen
                Path path = FileSystems.getDefault().getPath(filePath + nombreimagen + ".png");
                MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
                System.out.println("Se genero una imagen en la ruta: \n" + path);
            }
        }
    }

    static String obtenerIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
}
