package com.example.demo.repository;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ExamRepository extends JpaRepository<Exam,Long> {
 Optional<Exam>  findByTitle(String title);
}
