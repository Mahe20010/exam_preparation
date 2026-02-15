package com.example.demo.sevices.impl;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ExamService {
    void updateExamQuestions(MultipartFile file,Exam exam);
    //List<Question>  getAllExamQuestions(Long examId);
    List<Exam> getAllExams();
    Exam getExamByTitle(String title);
}
