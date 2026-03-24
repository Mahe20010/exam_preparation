package com.example.demo.controllers;

import com.example.demo.entity.StudentAnswer;
import com.example.demo.modules.ExamResultResponse;
import com.example.demo.modules.StudentAnswerRequest;
import com.example.demo.sevices.impl.StudentAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/student/exam")
public class StudentAnswerController {

    @Autowired
    private StudentAnswerService studentAnswerService;

    @PostMapping("/attempt/{attemptId}/submit")
    public String submitExam(
            @PathVariable Long attemptId,
            @RequestParam List<StudentAnswerRequest> studentAnswers
    ){
          studentAnswerService.saveAnswers(attemptId,studentAnswers);
return "Exam submitted successfully";
    }
    @PostMapping("/attempt/{attemptId}/save")
    public String saveExam(
            @PathVariable Long attemptId,
            @RequestParam StudentAnswerRequest studentAnswers
    ){
        studentAnswerService.saveAnswer(attemptId,studentAnswers);
        return "Exam submitted successfully";
    }
    @GetMapping("/attempt/{attemptId}/saved")
    public Map<Long, Integer> getSavedAnswer(
            @PathVariable Long attemptId,
            @RequestParam Long questionId
    ){
        StudentAnswer answer=studentAnswerService.getByAttemptIdAndQuestionId(attemptId,questionId);
        Map<Long,Integer> ans=new HashMap<>();
        ans.put(answer.getQuestion().getId(),answer.getSelectedOption());
        return ans;

        }
    }
//    @GetMapping("/attempt/{attemptId}/result")
//    public ExamResultResponse getResult(@PathVariable Long attemptId){
//        return e
//    }

