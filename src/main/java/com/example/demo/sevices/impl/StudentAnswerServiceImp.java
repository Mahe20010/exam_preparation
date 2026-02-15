package com.example.demo.sevices.impl;

import com.example.demo.entity.ExamAttempt;
import com.example.demo.entity.Question;
import com.example.demo.entity.StudentAnswer;
import com.example.demo.modules.StudentAnswerRequest;
import com.example.demo.repository.ExamAttemptRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.repository.StudentAnswerRepository;
import com.example.demo.sevices.ExamAttemptService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
@Service
public class StudentAnswerServiceImp implements StudentAnswerService{

    @Autowired
    private StudentAnswerRepository studentAnswerRepository;
    @Autowired
    private ExamAttemptRepository examAttemptRepository;
    @Autowired
    private QuestionRepository questionRepository;

    @Override
    public List<StudentAnswer> getByAttemptId(Long attemptId) {
        return studentAnswerRepository.findByExamAttemptId(attemptId);
    }

    @Transactional
    @Override
    public void saveAnswers(Long attemptId, List<StudentAnswerRequest> answers) {

        ExamAttempt examAttempt=examAttemptRepository.findById(attemptId).orElseThrow();
        if ("SUBMITTED".equals(examAttempt.getStatus())) {
            throw new RuntimeException("Exam already submitted");
        }

        double totalScore=0;
        for(StudentAnswerRequest ans:answers){
            Question question=questionRepository.findById(ans.getQuestionId()).orElseThrow();

            StudentAnswer studentAnswer=new StudentAnswer();

            studentAnswer.setExamAttempt(examAttempt);
            studentAnswer.setQuestion(question);
            studentAnswer.setSelectedOption(ans.getSelectedOption());
            if(Objects.equals(ans.getSelectedOption(),(question.getCorrectAnswer()))){
                studentAnswer.setCorrect(true);
                studentAnswer.setMarksObtained(1.0);
                totalScore+=1.0;
            }else {
                studentAnswer.setCorrect(false);
                studentAnswer.setMarksObtained(0.0);

            }
            studentAnswerRepository.save(studentAnswer);
        }
        examAttempt.setScore(totalScore);
        examAttempt.setStatus("SUBMITTED");
        examAttempt.setEndTime(LocalDateTime.now());
        examAttemptRepository.save(examAttempt);
    }
}
