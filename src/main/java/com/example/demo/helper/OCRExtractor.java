package com.example.demo.helper;
//import net.sourceforge.tess4j.*;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.rendering.PDFRenderer;
//
//import java.awt.image.BufferedImage;
//import java.nio.file.Path;
//
public class OCRExtractor {
//
//    public static String extractText(Path path) {
//
//        StringBuilder text = new StringBuilder();
//
//        try (PDDocument doc = PDDocument.load(path.toFile())) {
//
//            PDFRenderer renderer = new PDFRenderer(doc);
//            ITesseract tesseract = new Tesseract();
//
//            // 👉 SET YOUR TESSDATA PATH
//            tesseract.setDatapath("C:/Program Files/Tesseract-OCR/tessdata");
//
//            for (int i = 0; i < doc.getNumberOfPages(); i++) {
//
//                BufferedImage image = renderer.renderImageWithDPI(i, 300);
//
//                String pageText = tesseract.doOCR(image);
//
//                text.append(pageText).append("\n");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return text.toString();
//    }
}