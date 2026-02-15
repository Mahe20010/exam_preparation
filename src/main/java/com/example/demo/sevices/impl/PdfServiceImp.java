package com.example.demo.sevices.impl;

import com.example.demo.entity.Category;
import com.example.demo.entity.PdfFile;
import com.example.demo.repository.CategoryRepository;
import com.example.demo.repository.PdfRepository;
import com.example.demo.sevices.PdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PdfServiceImp implements PdfService {

    @Autowired
    private PdfRepository pdfRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    private final String baseDir="E:/pdf-uploads/";

    @Override
    public String uploadPdf(MultipartFile file, String title,String categoryName) throws IOException {
        Category category=categoryRepository.findByName(categoryName).
                orElseGet(()->{
                    Category newCategory = new Category();
                    newCategory.setName(categoryName);
                    newCategory.setCreatedAt(LocalDateTime.now());
                    return categoryRepository.save(newCategory);

                });
        Path categoryPath=Paths.get(baseDir+category.getId());
        if(!Files.exists(categoryPath)){
            Files.createDirectories(categoryPath);
        }
        String fileName = UUID.randomUUID() + "_" +file.getOriginalFilename();

        Path filepath = categoryPath.resolve(fileName);
        Files.write(filepath, file.getBytes());

        PdfFile pdf = new PdfFile();
        pdf.setTitle(title);
        pdf.setFileName(fileName);
        pdf.setCategory(category);
        pdf.setDateTime(LocalDateTime.now());

        pdfRepository.save(pdf);

        return "Uploaded successfully";
    }


    @Override
    public List<PdfFile> getAllPdfs() {
        return pdfRepository.findAll();
    }

    @Override
    public List<PdfFile> getByCategory(String categoryName) {
        Category category=categoryRepository.findByName(categoryName).
               orElseGet(()->{
                       Category newCategory = new Category();
        newCategory.setName(categoryName);
        newCategory.setCreatedAt(LocalDateTime.now());
        return categoryRepository.save(newCategory);
    });
        return pdfRepository.findByCategory(category);
    }

    @Override
    public Resource downloadPdf(String fileName) throws IOException {
        PdfFile pdf = pdfRepository.findByFileName(fileName)
                .orElseThrow(() -> new RuntimeException("File not found in database"));

        // 2️⃣ Build correct file path
        Path filePath = Paths.get(baseDir + pdf.getCategory().getId() + "/" + fileName);

        // 3️⃣ Load resource
        Resource resource = new UrlResource(filePath.toUri());

        if (!resource.exists()) {
            throw new RuntimeException("File not found on disk");
        }

        return resource;
    }

    @Override
    public Map<String,List<PdfFile>> getGroupPdfs(){
        List<PdfFile> pdfFileList=pdfRepository.findAll();
        return  pdfFileList.stream().collect(
                Collectors.groupingBy(
                        pdf->pdf.getCategory()!=null?pdf.getCategory().getName():"Uncategarised"
                )
        );
    }
}
