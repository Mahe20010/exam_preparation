package com.example.demo.repository;

import com.example.demo.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question,Long> {
    // List<Question> findByExamId(Long examId);
    Optional<Question> findById(Long questionId);

    // Get all subjects
    @Query("SELECT DISTINCT q.subject FROM Question q")
    List<String> findDistinctSubjects();

    // Get random questions per subject
    @Query(value = "SELECT * FROM question WHERE subject = :subject ORDER BY RANDOM() LIMIT :limit", nativeQuery = true)
    List<Question> findRandomBySubject(@Param("subject") String subject,
                                       @Param("limit") int limit);
}
