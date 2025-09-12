package com.example.demo.qrgenerator.builder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import java.io.IOException;

public class QRDirector {
    private final QRBuilder builder;

    public QRDirector(QRBuilder builder) {
        this.builder = builder;
    }

    public void construct(String url, int width, int height, String filePath, String fileName, BarcodeFormat format) throws WriterException, IOException {
        builder.setUrl(url);
        builder.setWidth(width);
        builder.setHeight(height);
        builder.setFilePath(filePath);
        builder.setFileName(fileName);
        builder.setFormat(format);
        builder.build();
    }
}
