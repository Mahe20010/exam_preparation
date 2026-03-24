package com.example.demo.sevices.impl;

import com.example.demo.entity.StudentAnswer;
import com.example.demo.modules.StudentAnswerRequest;
import com.example.demo.repository.StudentAnswerRepository;

import java.util.List;


public interface StudentAnswerService  {
    List<StudentAnswer> getByAttemptId(Long attemptId);
    StudentAnswer getByAttemptIdAndQuestionId(Long attemptId,Long questionId);
    void saveAnswer(Long attemptId,StudentAnswerRequest answer);
    void saveAnswers(Long attemptId,
                     List<StudentAnswerRequest> answers);
}
