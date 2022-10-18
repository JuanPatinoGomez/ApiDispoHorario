package com.example.demo.qrgenerator.service;

import com.google.zxing.WriterException;

import java.io.IOException;

public interface IQRService {

    void qrApp(String urlApp) throws WriterException, IOException;
    void allQrs() throws IOException, WriterException;
    void sedeQrs(Long idSede) throws IOException, WriterException;
    void edificioQrs(Long idEdificio) throws IOException, WriterException;
    void salonQr(Long idSalon) throws IOException, WriterException;

}
