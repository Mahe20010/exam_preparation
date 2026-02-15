package com.example.demo.controllers;

import com.example.demo.entity.PdfFile;
import com.example.demo.sevices.PdfService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pdf")
@CrossOrigin("*")
public class PdfController {

    @Autowired
    private PdfService pdfService;

    @PostMapping(
            value = "/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<String> uploadPdf(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("categoryName")String categoryName) throws IOException {

        return ResponseEntity.ok(pdfService.uploadPdf(file, title,categoryName));
    }

    @GetMapping("/all")
    public List<PdfFile> getAll() {
        return pdfService.getAllPdfs();
    }
    @GetMapping("/category/{name}")
    public List<PdfFile> getCategory(@PathVariable String name){
        return pdfService.getByCategory(name);
    }
    @GetMapping("/grouped")
    public ResponseEntity<Map<String,List<PdfFile>>> getGrouped(){
        return  ResponseEntity.ok(pdfService.getGroupPdfs());
    }
    @GetMapping("/download/{fileName}")
    public ResponseEntity<Resource> download(@PathVariable String fileName) throws IOException {

        Resource resource = pdfService.downloadPdf(fileName);
        System.out.println("Requested file: " + fileName);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + fileName + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

}