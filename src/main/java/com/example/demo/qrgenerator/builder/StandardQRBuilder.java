package com.example.demo.qrgenerator.builder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class StandardQRBuilder implements QRBuilder {
    private String url;
    private int width = 300;
    private int height = 300;
    private String filePath = "./";
    private String fileName = "qr";
    private BarcodeFormat format = BarcodeFormat.QR_CODE;

    @Override
    public void setUrl(String url) { this.url = url; }
    @Override
    public void setWidth(int width) { this.width = width; }
    @Override
    public void setHeight(int height) { this.height = height; }
    @Override
    public void setFilePath(String filePath) { this.filePath = filePath; }
    @Override
    public void setFileName(String fileName) { this.fileName = fileName; }
    @Override
    public void setFormat(BarcodeFormat format) { this.format = format; }

    @Override
    public void build() throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(url, format, width, height);
        Path path = FileSystems.getDefault().getPath(filePath + fileName + ".png");
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
        System.out.println("Se genero una imagen en la ruta: \n" + path);
    }
}
