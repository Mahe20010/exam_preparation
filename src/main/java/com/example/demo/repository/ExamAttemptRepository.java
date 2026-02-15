package com.example.demo.repository;

import com.example.demo.entity.ExamAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt,Long> {
    List<ExamAttempt> findByUser_IdAndExam_Id(Long userId,Long examId);
    Optional<ExamAttempt> findById(Long attemptId);
    Optional<ExamAttempt> findByIdAndUser_IdAndExam_Id(Long attemptId,Long userId,Long examId);
    List<ExamAttempt> findByUserId(Long studentId);
}
