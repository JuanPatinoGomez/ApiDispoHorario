package com.example.demo.qrgenerator;

import com.example.demo.entity.Edificio;
import com.example.demo.entity.Salon;
import com.example.demo.entity.Sede;
import com.example.demo.entity.dto.InformacionQRDTO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
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

    public static BitMatrix generateQRCodeImage(String url, int width, int height) throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, BarcodeFormat.QR_CODE, width, height);
        return bitMatrix;

    }

    public static BitMatrix generateQRSalonImage(Long idSalon, int width, int height) throws WriterException, IOException {

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        //Codigo qr con la url
        BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + idSalon, BarcodeFormat.QR_CODE, width, height);
        return bitMatrix;

    }

    public static List<InformacionQRDTO> generateQREdificioImage(HashMap<Long, Integer> salones, String nombreEdificio, String sedeMunicipio, int width, int height) throws WriterException, IOException {
        List<InformacionQRDTO> informacionQRDTOList = new ArrayList<>();
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        for(HashMap.Entry<Long, Integer> entry : salones.entrySet()){
            InformacionQRDTO informacionQRDTO = new InformacionQRDTO();
            //Codigo qr con la url
            BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + entry.getKey(), BarcodeFormat.QR_CODE, width, height);
            String nombreimagen = sedeMunicipio + "_" + nombreEdificio + "_" + entry.getValue();
            informacionQRDTO.setBitMatrix(bitMatrix);
            informacionQRDTO.setNombreArchivo(nombreimagen);
            informacionQRDTOList.add(informacionQRDTO);

        }

        return informacionQRDTOList;
    }

    public static List<InformacionQRDTO> generateQRSedeImage(Sede sede, int width, int height) throws IOException, WriterException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();

        return recorrerEdificios(sede, width, height, qrCodeWriter);
    }

    public static List<InformacionQRDTO> generateQRAllImage(List<Sede> sedes, int width, int height) throws IOException, WriterException{
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        List<InformacionQRDTO> informacionQRDTOList = new ArrayList<>();

        for(Sede sede: sedes){
            informacionQRDTOList.addAll(recorrerEdificios(sede, width, height, qrCodeWriter));
        }
        return informacionQRDTOList;
    }

    private static List<InformacionQRDTO> recorrerEdificios(Sede sede, int width, int height, QRCodeWriter qrCodeWriter) throws WriterException, IOException {
        List<InformacionQRDTO> informacionQRDTOList = new ArrayList<>();
        for(Edificio edificio: sede.getEdificios()){
            for(Salon salon : edificio.getSalones()){
                InformacionQRDTO informacionQRDTO = new InformacionQRDTO();
                //Codigo qr con la url
                BitMatrix bitMatrix = qrCodeWriter.encode(urlApp + "/clases/salon/" + salon.getId(), BarcodeFormat.QR_CODE, width, height);
                String nombreimagen = sede.getMunicipio() + "_" + edificio.getNombre() + "_" + salon.getNumero();
                informacionQRDTO.setBitMatrix(bitMatrix);
                informacionQRDTO.setNombreArchivo(nombreimagen);
                informacionQRDTOList.add(informacionQRDTO);

            }
        }
        return informacionQRDTOList;
    }

    static String obtenerIP() throws UnknownHostException {
        InetAddress ip = InetAddress.getLocalHost();
        return ip.getHostAddress();
    }
}
