package com.example.demo.qrgenerator.service;

import com.example.demo.entity.dto.InformacionQRDTO;
import com.example.demo.repository.IEdificioRepository;
import com.example.demo.repository.ISalonRepository;
import com.example.demo.repository.ISedeRepository;
import com.example.demo.entity.Edificio;
import com.example.demo.entity.Salon;
import com.example.demo.entity.Sede;
import com.example.demo.qrgenerator.QRCodeGenerator;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class QRServiceImpl implements IQRService{

    @Autowired
    private ISedeRepository sedeDao;
    @Autowired
    private IEdificioRepository edificioDao;
    @Autowired
    private ISalonRepository salonDao;
    @Override
    public BitMatrix qrApp(String urlApp) throws WriterException, IOException {
        return QRCodeGenerator.generateQRCodeImage(urlApp, 1024, 1024);
        //QRCodeGenerator.generateQRCodeImage(urlApp + path, 1024, 1024, "C:\\Universidad\\Trabajo_grado\\qr\\prueba-generarqr\\qr.png");
        //return QRCodeGenerator.getQRCodeImage(path, 1024, 1024);
        //QRCodeGenerator.generateQRCodeImage(urlApp, 1024,1024, path);
    }

    @Override
    public List<InformacionQRDTO> allQrs() throws IOException, WriterException {
        List<Sede> sedes = sedeDao.findAll();
        return QRCodeGenerator.generateQRAllImage(sedes, 1024, 1024);
    }

    @Override
    public List<InformacionQRDTO> sedeQrs(Long idSede) throws IOException, WriterException {
        Sede sede = sedeDao.findById(idSede).orElse(null);
        return QRCodeGenerator.generateQRSedeImage(sede, 1024,1024);

    }

    @Override
    public List<InformacionQRDTO> edificioQrs(Long idEdificio) throws IOException, WriterException {
        Edificio edificio = edificioDao.findById(idEdificio).orElse(null);
        String sedeMunicipio = edificio.getSede().getMunicipio();
        //id-numero
        HashMap<Long, Integer> salones = new HashMap<>();
        edificio.getSalones().forEach(salon -> salones.put(salon.getId(), salon.getNumero()));

        return QRCodeGenerator.generateQREdificioImage(salones, edificio.getNombre(), sedeMunicipio, 1024,1024);
    }

    @Override
    public BitMatrix salonQr(Long idSalon) throws IOException, WriterException {
        Salon salon = salonDao.findById(idSalon).orElse(null);
        Edificio edificio = salon.getEdificio();
        String sedeMunicipio = edificio.getSede().getMunicipio();

        return QRCodeGenerator.generateQRSalonImage(salon.getId(), 1024,1024);

    }
}
