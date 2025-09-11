package com.example.demo.qrgenerator.service;

import com.example.demo.repository.IEdificioRepository;
import com.example.demo.repository.ISalonRepository;
import com.example.demo.repository.ISedeRepository;
import com.example.demo.entity.Edificio;
import com.example.demo.entity.Salon;
import com.example.demo.entity.Sede;
import com.example.demo.qrgenerator.QRCodeGenerator;
import com.google.zxing.WriterException;
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
    public void qrApp(String urlApp) throws WriterException, IOException {
        QRCodeGenerator.getInstancia().generateQRCodeImage(urlApp, 200, 200, "C:\\Universidad\\Trabajo_grado\\qr\\prueba-generarqr\\qr.png");
    }

    @Override
    public void allQrs() throws IOException, WriterException {
        List<Sede> sedes = sedeDao.findAll();
        QRCodeGenerator.getInstancia().generateQRAllImage(sedes, 200, 200, "C:\\Universidad\\Trabajo_grado\\qr\\prueba-generarqr\\");
    }

    @Override
    public void sedeQrs(Long idSede) throws IOException, WriterException {
        Sede sede = sedeDao.findById(idSede).orElse(null);
        QRCodeGenerator.getInstancia().generateQRSedeImage(sede, 200,200, "C:\\Universidad\\Trabajo_grado\\qr\\prueba-generarqr\\");
    }

    @Override
    public void edificioQrs(Long idEdificio) throws IOException, WriterException {
        Edificio edificio = edificioDao.findById(idEdificio).orElse(null);
        String sedeMunicipio = edificio.getSede().getMunicipio();
        HashMap<Long, Integer> salones = new HashMap<>();
        edificio.getSalones().forEach(salon -> salones.put(salon.getId(), salon.getNumero()));
        QRCodeGenerator.getInstancia().generateQREdificioImage(salones, edificio.getNombre(), sedeMunicipio, 200,200, "C:\\Universidad\\Trabajo_grado\\qr\\prueba-generarqr\\");
    }

    @Override
    public void salonQr(Long idSalon) throws IOException, WriterException {
        Salon salon = salonDao.findById(idSalon).orElse(null);
        Edificio edificio = salon.getEdificio();
        String sedeMunicipio = edificio.getSede().getMunicipio();
        QRCodeGenerator.getInstancia().generateQRSalonImage(salon.getId(), salon.getNumero(), edificio.getNombre(), sedeMunicipio, 200,200, "C:\\Universidad\\Trabajo_grado\\qr\\prueba-generarqr\\");
    }
}
