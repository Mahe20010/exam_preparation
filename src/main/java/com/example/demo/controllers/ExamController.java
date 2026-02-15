package com.example.demo.controllers;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import com.example.demo.sevices.impl.ExamService;
import com.example.demo.sevices.impl.ExamServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/exam")
public class ExamController {
    @Autowired
    private ExamServiceImp examServiceImp;

    @PostMapping(value = "/update-exam",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public void updateExam(
            @RequestParam("file")MultipartFile file,
            @RequestParam("title")String title,
            @RequestParam int durationMinutes,
            @RequestParam int totalMarks,
            @RequestParam int negativeMarks
            ){
        Exam exam =new Exam();
        exam.setTitle(title);
        exam.setDurationMinutes(durationMinutes);
        exam.setNegativeMarks(negativeMarks);
        exam.setTotalMarks(totalMarks);
        examServiceImp.updateExamQuestions(file,exam);
        //.setQuestionList(questionList);
    }
    @GetMapping("/all")
    public List<Exam> getAllExams(){
        return examServiceImp.getAllExams();
    }
    @GetMapping("/exam_by_title/")
    public Exam getExam(@RequestParam String title){
        return examServiceImp.getExamByTitle(title);
    }
//    @GetMapping("/exam-questions")
//    public List<Question> getAllExamQuestions(){
//        return examServiceImp.getAllExamQuestions();
//    }
}
