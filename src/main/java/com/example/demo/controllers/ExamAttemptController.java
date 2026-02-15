package com.example.demo.controllers;

import com.example.demo.entity.ExamAttempt;
import com.example.demo.modules.ExamResultResponse;
import com.example.demo.modules.StudentAnswerRequest;
import com.example.demo.sevices.ExamAttemptService;
import com.example.demo.sevices.impl.ExamAttemptServiceImp;
import com.example.demo.sevices.impl.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/exam")
public class ExamAttemptController {
    @Autowired
    private ExamAttemptService examAttemptService;
    @Autowired
    private StudentAnswerService studentAnswerService;

    @PostMapping("/{examId}/start")
    public ExamAttempt startExam(
            @PathVariable Long examId,
            @RequestParam Long studentId
    ){
        return examAttemptService.startExam(examId,studentId);
    }

    @PostMapping("/attempt/{attemptId}/submit")
    public String submitExam(
            @PathVariable Long attemptId,
            @RequestBody List<StudentAnswerRequest> studentAnswers
    ){
        studentAnswerService.saveAnswers(attemptId,studentAnswers);
        return "Exam submitted successfully";
    }
    @GetMapping("/{examId}/attempt/{attemptId}/result")
    public ExamResultResponse getResult(

            @PathVariable Long attemptId,
            @PathVariable Long examId,
            @RequestParam Long studentId

    ){
        return examAttemptService.getResult(attemptId,studentId,examId);
    }
    @GetMapping("/{studentId}/all-exam-attempts")
    public List<ExamAttempt> allAttempts(
            @PathVariable Long studentId
           // @PathVariable Long examId
    ){

        return  examAttemptService.getByStudentId(studentId);
    }

}
