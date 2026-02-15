package com.example.demo.sevices;

import com.example.demo.entity.PdfFile;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface PdfService {
    String uploadPdf(MultipartFile file, String title,String categoryName) throws IOException;
    List<PdfFile> getAllPdfs();
    List<PdfFile> getByCategory(String categoryName);
    Resource downloadPdf(String fileName) throws IOException;
    Map<String,List<PdfFile>> getGroupPdfs();
}
