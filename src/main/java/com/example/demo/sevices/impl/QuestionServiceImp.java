package com.example.demo.sevices.impl;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.sevices.QuestionService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionServiceImp implements QuestionService {
    @Autowired
    private QuestionRepository questionRepository;
//    @Autowired
//    private ExamRepository examRepository;
 //   private String basedir="E:/upload_questions/test/";
    @Override
    public Question updateQuestion(Question question) {
        return null;
    }

    public void saveFromExcel(MultipartFile file){
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
               // question.setCorrectAnswer(formatter.formatCellValue(row.getCell(correctAnswerIndex)));
                String answerStr = formatter.formatCellValue(row.getCell(correctAnswerIndex));

                int answer = processAnswer(answerStr);


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
    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }


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
