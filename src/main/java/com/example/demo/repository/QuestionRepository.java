package com.example.demo.repository;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {
   // List<Question> findByExamId(Long examId);
    Optional<Question> findById(Long questionId);

}
