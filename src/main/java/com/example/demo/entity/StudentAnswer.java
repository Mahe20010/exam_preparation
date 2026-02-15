package com.example.demo.entity;

import jakarta.persistence.*;

@Entity
public class StudentAnswer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ExamAttempt examAttempt;
    @ManyToOne
    private Question question;
    private  Integer selectedOption;
    private Boolean isCorrect;
    private Double marksObtained;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getCorrect() {
        return isCorrect;
    }

    public Double getMarksObtained() {
        return marksObtained;
    }

    public Integer getSelectedOption() {
        return selectedOption;
    }

    public ExamAttempt getExamAttempt() {
        return examAttempt;
    }

    public Question getQuestion() {
        return question;
    }

    public void setCorrect(Boolean correct) {
        isCorrect = correct;
    }

    public void setExamAttempt(ExamAttempt examAttempt) {
        this.examAttempt = examAttempt;
    }

    public void setMarksObtained(Double marksObtained) {
        this.marksObtained = marksObtained;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public void setSelectedOption(Integer selectedOption) {
        this.selectedOption = selectedOption;
    }

}
