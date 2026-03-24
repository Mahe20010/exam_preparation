package com.example.demo.sevices.schedulars;

import com.example.demo.entity.ExamAttempt;
import com.example.demo.repository.ExamAttemptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ExamSchedulerService {
    @Autowired
    private ExamAttemptRepository examAttemptRepository;
    @Scheduled(fixedRate = 60000)
    public void autoSubmitExpiredExams(){
        List<ExamAttempt> activeAttempts=examAttemptRepository.findByStatus("STARTED");
        for(ExamAttempt attempt:activeAttempts){
            LocalDateTime endTime=attempt.getStartTime().plusMinutes(attempt.getExam().getDurationMinutes());
            if(LocalDateTime.now().isAfter(endTime)){
                attempt.setStatus("SUBMITTED");
                attempt.setEndTime(LocalDateTime.now());
                examAttemptRepository.save(attempt);

            }
        }
    }
}
