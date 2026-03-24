package com.example.demo.helper;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PdfToImageConverter {

    public static List<BufferedImage> convert(File file) {

        List<BufferedImage> images = new ArrayList<>();

        try (PDDocument doc = PDDocument.load(file)) {

            PDFRenderer renderer = new PDFRenderer(doc);

            for (int i = 0; i < doc.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 300);
                images.add(image);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return images;
    }
}