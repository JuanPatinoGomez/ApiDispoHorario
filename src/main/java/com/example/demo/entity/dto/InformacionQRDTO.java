package com.example.demo.entity.dto;

import com.google.zxing.common.BitMatrix;

public class InformacionQRDTO {

    private BitMatrix bitMatrix;
    private String nombreArchivo;

    public InformacionQRDTO() {
    }

    public InformacionQRDTO(BitMatrix bitMatrix, String nombreArchivo) {
        this.bitMatrix = bitMatrix;
        this.nombreArchivo = nombreArchivo;
    }

    public BitMatrix getBitMatrix() {
        return bitMatrix;
    }

    public void setBitMatrix(BitMatrix bitMatrix) {
        this.bitMatrix = bitMatrix;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }
}
