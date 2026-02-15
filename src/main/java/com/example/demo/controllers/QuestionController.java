package com.example.demo.controllers;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import com.example.demo.repository.ExamRepository;
import com.example.demo.sevices.impl.ExamServiceImp;
import com.example.demo.sevices.impl.QuestionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/create-exam")
public class QuestionController {

    @Autowired
    private QuestionServiceImp questionServiceImp;
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ExamServiceImp examServiceImp;
    private Exam savedExam;
    @PostMapping(value = "/exam-questions",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String questionsUpdate(
            @RequestParam("file") MultipartFile file,
            @RequestParam String title,
            @RequestParam int durationMinutes,
            @RequestParam int totalMarks,
            @RequestParam int negativeMarks
    ){
        Exam exam=new Exam();
        exam.setTitle(title);
        exam.setDurationMinutes(durationMinutes);
        exam.setTotalMarks(totalMarks);
        exam.setNegativeMarks(negativeMarks);
        exam.setCreatedAt(LocalDateTime.now());
        savedExam=examRepository.save(exam);
        questionServiceImp.saveFromExcel(file);
        return "Exam and questions saved";

    }
    @GetMapping("/all")
    public List<Question> getAllQuestions(){
        return questionServiceImp.getAllQuestions();
    }

}
