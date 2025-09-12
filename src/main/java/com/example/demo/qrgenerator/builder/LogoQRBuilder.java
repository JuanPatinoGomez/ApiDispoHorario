package com.example.demo.qrgenerator.builder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class LogoQRBuilder implements QRBuilder {
    private String url;
    private int width = 300;
    private int height = 300;
    private String filePath = "./";
    private String fileName = "qr_logo";
    private BarcodeFormat format = BarcodeFormat.QR_CODE;
    private BufferedImage logo;

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void setFormat(BarcodeFormat format) {
        this.format = format;
    }

    public void setLogo(BufferedImage logo) {
        this.logo = logo;
    }

    @Override
    public void build() throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // Alta corrección
        BitMatrix bitMatrix = qrCodeWriter.encode(url, format, width, height, hints);
        BufferedImage qrGray = MatrixToImageWriter.toBufferedImage(bitMatrix);

        // Crea una imagen ARGB y copia el QR
        BufferedImage qrImage = new BufferedImage(qrGray.getWidth(), qrGray.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = qrImage.createGraphics();
        g2.drawImage(qrGray, 0, 0, null);
        // ...logo drawing code below...
        if (logo != null) {
            int logoWidth = qrImage.getWidth() / 6;
            int logoHeight = qrImage.getHeight() / 6;
            int logoX = (qrImage.getWidth() - logoWidth) / 2;
            int logoY = (qrImage.getHeight() - logoHeight) / 2;

            g2.setColor(java.awt.Color.WHITE);
            g2.fillRoundRect(logoX, logoY, logoWidth, logoHeight, logoWidth / 4, logoHeight / 4);
            g2.drawImage(logo, logoX, logoY, logoWidth, logoHeight, null);
        }
        g2.dispose();

        Path path = FileSystems.getDefault().getPath(filePath + fileName + ".png");
        ImageIO.write(qrImage, "PNG", path.toFile());
        System.out.println("Se genero una imagen QR con logo en la ruta: \n" + path);
    }
}
