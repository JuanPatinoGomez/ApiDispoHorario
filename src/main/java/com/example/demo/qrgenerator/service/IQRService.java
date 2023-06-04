package com.example.demo.qrgenerator.service;

import com.example.demo.entity.dto.InformacionQRDTO;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.IOException;
import java.util.List;

public interface IQRService {

    BitMatrix qrApp(String urlApp) throws WriterException, IOException;
    List<InformacionQRDTO> allQrs() throws IOException, WriterException;
    List<InformacionQRDTO> sedeQrs(Long idSede) throws IOException, WriterException;
    List<InformacionQRDTO> edificioQrs(Long idEdificio) throws IOException, WriterException;
    BitMatrix salonQr(Long idSalon) throws IOException, WriterException;
}
