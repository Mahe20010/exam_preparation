package com.example.demo.sevices;

import com.example.demo.entity.ExamAttempt;
import com.example.demo.modules.ExamResultResponse;
import com.example.demo.repository.ExamAttemptRepository;

import java.util.List;

public interface ExamAttemptService  {

    public ExamAttempt startExam(Long examId,Long studentId);
    List<ExamAttempt> getByStudentId(Long studentId);
    ExamResultResponse getResult(Long attemptId);
    ExamResultResponse getResult(Long attemptId,Long userId,Long examId);
    List<ExamAttempt> getByStudentIdAndExamID(Long studentId,Long examID);
}
