package com.example.demo.sevices.impl;

import com.example.demo.entity.Exam;
import com.example.demo.entity.ExamAttempt;
import com.example.demo.entity.StudentAnswer;
import com.example.demo.entity.User;
import com.example.demo.modules.ExamResultResponse;
import com.example.demo.repository.ExamAttemptRepository;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.StudentAnswerRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.sevices.ExamAttemptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ExamAttemptServiceImp implements ExamAttemptService {

    @Autowired
    private ExamAttemptRepository examAttemptRepository;
   @Autowired
    private ExamRepository examRepository;
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private StudentAnswerRepository studentAnswerRepository;
    @Override
    public List<ExamAttempt> getByStudentIdAndExamID(Long studentId,Long examId){
        return examAttemptRepository.findByUser_IdAndExam_Id(studentId,examId);
    }

    @Override
    public ExamResultResponse getResult(Long attemptId) {
        ExamAttempt examAttempt=examAttemptRepository.findById(attemptId).orElseThrow();
        List<StudentAnswer> answers=studentAnswerRepository.findByExamAttemptId(attemptId);
        int totalQuestions=answers.size();
        int correctAnswers=0;
        int wrongAnswer=0;
        for(StudentAnswer ans:answers){
            if(Boolean.TRUE.equals(ans.getCorrect())){
                correctAnswers++;
            }else {
                wrongAnswer++;
            }

        }
        double totalMarks=examAttempt.getScore();
        double percentage=(totalMarks/totalQuestions)*100;
        ExamResultResponse response=new ExamResultResponse();
        response.setCorrectAnswers(correctAnswers);
        response.setPercentage(percentage);
        response.setTotalMarks(totalMarks);
        response.setWrongAnswers(wrongAnswer);
        response.setTotalQuestions(totalQuestions);
        return response;
    }

    @Override
    public ExamResultResponse getResult(Long attemptId, Long userId, Long examId) {
        ExamAttempt examAttempt=examAttemptRepository.findByIdAndUser_IdAndExam_Id(attemptId,userId,examId).orElseThrow();
        List<StudentAnswer> answers=studentAnswerRepository.findByExamAttemptId(attemptId);
        int totalQuestions=answers.size();
        int correctAnswers=0;
        int wrongAnswer=0;
        for(StudentAnswer ans:answers){
            if(Boolean.TRUE.equals(ans.getCorrect())){
                correctAnswers++;
            }else {
                wrongAnswer++;
            }

        }
        double totalMarks;
        if(examAttempt.getScore()==null){
            totalMarks=0;
        }else {
            totalMarks = examAttempt.getScore();
        }
        double percentage=(totalMarks/totalQuestions)*100;
        ExamResultResponse response=new ExamResultResponse();
        response.setCorrectAnswers(correctAnswers);
        response.setPercentage(percentage);
        response.setTotalMarks(totalMarks);
        response.setWrongAnswers(wrongAnswer);
        response.setTotalQuestions(totalQuestions);
        return response;
    }

    public ExamAttempt startExam(Long examId,Long studentId){
        Exam exam =examRepository.findById(examId).orElseThrow(()->new RuntimeException("exam not found"));
        User user=userRepository.findById(studentId).orElseThrow(()->new RuntimeException("unknown user"));
        ExamAttempt attempt=new ExamAttempt();
        attempt.setExam(exam);
        attempt.setUser(user);
        attempt.setStartTime(LocalDateTime.now());
        attempt.setStatus("STARTED");
        return examAttemptRepository.save(attempt);

    }

    @Override
    public List<ExamAttempt> getByStudentId(Long studentId) {
        List<ExamAttempt> examList=examAttemptRepository.findByUserId(studentId);
        for(ExamAttempt examAttempt:examList){
            examAttempt.getExam().setQuestionList(null);
        }
        return examList;
    }

}
