package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy  =GenerationType.IDENTITY)
    private Long id;
    private int correctAnswer;
    private String questionText;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private  String subject;
    private String difficulty;
    @ManyToOne
    @JoinColumn(name="exam_id")
    @JsonIgnore
    private Exam exam;


    public Exam getExam() {
        return exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }

    public String getOption1() {
        return option1;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }


    public void setOptions(String option1) {
        this.option1= option1;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public String getSubject() {
        return subject;
    }

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
