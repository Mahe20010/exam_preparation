package com.example.demo.sevices;

import com.example.demo.entity.Question;

import java.util.List;

public interface QuestionService {

    Question updateQuestion(Question question);
    List<Question> getAllQuestions();

}
