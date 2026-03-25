package com.example.demo.sevices.impl;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.QuestionRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class ExamServiceImp implements ExamService{
    @Autowired
    private ExamRepository examRepository;
   @Autowired
   private QuestionRepository questionRepository;


    @Override
    public Map<String, Object> generateRandomExam() {

        Map<String, Object> response = new HashMap<>();

        // 1️⃣ Create Exam with dynamic title
        Exam exam = new Exam();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        System.out.println("ooooooooooooooooooooooooooooooooooooo");
        exam.setTitle("Mock Test - " + LocalDateTime.now().format(formatter));
exam.setCreatedAt(LocalDateTime.now());
        Exam savedExam = examRepository.save(exam);

        List<Question> finalQuestions = new ArrayList<>();

        // 2️⃣ Get all subjects
        List<String> subjects = questionRepository.findDistinctSubjects();

        // 3️⃣ Loop each subject → get 4 random questions
        for (String subject : subjects) {

            List<Question> randomQuestions =
                    questionRepository.findRandomBySubject(subject, 4);

            // 4️⃣ Clone questions (IMPORTANT)
            for (Question q : randomQuestions) {

                Question newQ = new Question();

                newQ.setSubject(q.getSubject());
                newQ.setQuestionText(q.getQuestionText());
                newQ.setOption1(q.getOption1());
                newQ.setOption2(q.getOption2());
                newQ.setOption3(q.getOption3());
                newQ.setOption4(q.getOption4());
                newQ.setCorrectAnswer(q.getCorrectAnswer());
                newQ.setDifficulty(q.getDifficulty());
                newQ.setReview(q.getReview());

                newQ.setExam(savedExam);

                finalQuestions.add(newQ);
               // savedExam.setQuestionList(finalQuestions);
                System.out.println("-------------------------------------------------------------");
                System.out.println(savedExam.getQuestionList());
            }
        }

        // 5️⃣ Shuffle questions
        Collections.shuffle(finalQuestions);

        // 6️⃣ Save new exam questions
        questionRepository.saveAll(finalQuestions);

        // 7️⃣ Prepare response
        response.put("examId", savedExam.getId());
        response.put("title", savedExam.getTitle());
        response.put("questions", finalQuestions);

        return response;
    }

    @Override
    public void updateExamQuestions(MultipartFile file,Exam exam) {

            int difficultyIndex=-1;
            int option1Index=-1;
            int option2Index=-1;
            int option3Index=-1;
            int option4Index=-1;
            int subjectIndex=-1;
            int questionTextIndex=-1;
            int correctAnswerIndex=-1;
            int reviewIndex=-1;
            // Exam exam= examRepository.findById(examId).orElseThrow(()->new RuntimeException("Exam not found"));
            DataFormatter formatter = new DataFormatter(); // Add this line

            try {
                XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
                XSSFSheet sheet = workbook.getSheetAt(0);
                List<Question> questions = new ArrayList<>();

                // 1. Header Row Logic (Remains mostly the same)
                XSSFRow headerRow = sheet.getRow(0);
                for (Cell cell : headerRow) {
                    String columnName = cell.getStringCellValue();
                    switch (columnName) {
                        case "Section": subjectIndex = cell.getColumnIndex(); break;
                        case "Question_Text": questionTextIndex = cell.getColumnIndex(); break;
                        case "Option1": option1Index = cell.getColumnIndex(); break;
                        case "Option2": option2Index = cell.getColumnIndex(); break;
                        case "Option3": option3Index = cell.getColumnIndex(); break;
                        case "Option4": option4Index = cell.getColumnIndex(); break;
                        case "Correct_Answer(1-4)": correctAnswerIndex = cell.getColumnIndex(); break;
                        case "Difficulty_Level": difficultyIndex = cell.getColumnIndex(); break;
                        case "Review/Explanation": reviewIndex = cell.getColumnIndex(); break;
                    }
                }

                // 2. Data Row Logic (Use formatter here)
                for (int i = 1; i <= sheet.getLastRowNum(); i++) { // Changed < to <= to get the last row
                    XSSFRow row = sheet.getRow(i);
                    if (row == null) continue;

                    Question question = new Question();

                    // Use formatter.formatCellValue(cell) for EVERYTHING
                    question.setSubject(formatter.formatCellValue(row.getCell(subjectIndex)));
                    question.setQuestionText(formatter.formatCellValue(row.getCell(questionTextIndex)));
                    question.setOption1(formatter.formatCellValue(row.getCell(option1Index)));
                    question.setOption2(formatter.formatCellValue(row.getCell(option2Index)));
                    question.setOption3(formatter.formatCellValue(row.getCell(option3Index)));
                    question.setOption4(formatter.formatCellValue(row.getCell(option4Index)));

                    // This safely handles numeric answers like 1, 2, 3
                  //  question.setCorrectAnswer(formatter.formatCellValue(row.getCell(correctAnswerIndex)));
                    String answerStr = formatter.formatCellValue(row.getCell(correctAnswerIndex));
                    int answer=processAnswer(answerStr);

//                    int answer = 0;
//                    try {
//                        answer = Integer.parseInt(answerStr.trim());
//                    } catch (Exception e) {
//                        System.out.println("Invalid correct answer at row " + i);
//                    }

                    question.setCorrectAnswer(answer);
                    question.setDifficulty(formatter.formatCellValue(row.getCell(difficultyIndex)));
                    question.setReview(formatter.formatCellValue(row.getCell(reviewIndex)));

                    questions.add(question);
                }
                workbook.close();
                questionRepository.saveAll(questions);
            } catch (IOException e) {
                throw new RuntimeException("Error while uploading excel file: " + e.getMessage());

            }

        }
       // exam.setQuestionList(questions);
       // examRepository.save(exam);
      //  return questions;


//    @Override
//    public List<Question> getAllExamQuestions(Long examId) {
//        return questionRepository.findByExamId(examId);
//    }

    @Override
    public List<Exam> getAllExams() {
        return examRepository.findAll();
    }

    @Override
    public Exam getExamByTitle(String title) {
        return examRepository.findByTitle(title).
                orElseThrow(()->new RuntimeException("Exam not found with tittle : "+title));
    }

//    @Override
//    public Exam getExam() {
//        return examRepository.findById();
//    }
public int processAnswer(String answerStr) { // 'int' means it returns a NUMBER
    String cleanStr = answerStr.replaceAll("[\\(\\)\\[\\]\\s]", "").toUpperCase();
    switch (cleanStr) {
        case "A": return 1;
        case "B": return 2;
        case "C": return 3;
        case "D": return 4;
        default:
            // You MUST return something if it's not A, B, C, or D
            try {
                return (int) Double.parseDouble(cleanStr);
            } catch (Exception e) {
                return 0;
            }
    }
}
}
