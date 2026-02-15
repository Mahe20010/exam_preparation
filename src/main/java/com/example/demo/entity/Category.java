package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private List<PdfFile> pdfFiles;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<PdfFile> getPdfFiles() {
        return pdfFiles;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPdfFiles(List<PdfFile> pdfFiles) {
        this.pdfFiles = pdfFiles;
    }

    public String getName() {
        return name;
    }


}
