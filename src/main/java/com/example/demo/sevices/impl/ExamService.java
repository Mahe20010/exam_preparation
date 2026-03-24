package com.example.demo.sevices.impl;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface ExamService {
    Map<String, Object> generateRandomExam();

    void updateExamQuestions(MultipartFile file, Exam exam);
    //List<Question>  getAllExamQuestions(Long examId);
    List<Exam> getAllExams();
    Exam getExamByTitle(String title);
}
