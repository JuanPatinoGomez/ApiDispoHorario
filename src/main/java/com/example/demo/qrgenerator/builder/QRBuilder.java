package com.example.demo.qrgenerator.builder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import java.io.IOException;

public interface QRBuilder {
    void setUrl(String url);
    void setWidth(int width);
    void setHeight(int height);
    void setFilePath(String filePath);
    void setFileName(String fileName);
    void setFormat(BarcodeFormat format);
    void build() throws WriterException, IOException;
}
