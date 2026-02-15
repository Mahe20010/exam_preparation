package com.example.demo.repository;

import com.example.demo.entity.Category;
import com.example.demo.entity.PdfFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PdfRepository extends JpaRepository<PdfFile,Long> {
    List<PdfFile> findByCategory(Category category);
    Optional<PdfFile> findByFileName(String fileName);

}
