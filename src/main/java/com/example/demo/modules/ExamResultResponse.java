package com.example.demo.modules;

public class ExamResultResponse {
    private double totalMarks;
    private int totalQuestions;
    private int correctAnswers;
    private int wrongAnswers;
    private double percentage;

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public double getTotalMarks() {
        return totalMarks;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getWrongAnswers() {
        return wrongAnswers;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setTotalMarks(double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void setWrongAnswers(int wrongAnswers) {
        this.wrongAnswers = wrongAnswers;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
