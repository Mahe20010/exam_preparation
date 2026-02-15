package com.example.demo.sevices.impl;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.QuestionRepository;
import com.example.demo.sevices.QuestionService;
import org.apache.poi.ss.usermodel.Cell;
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
       // Exam exam= examRepository.findById(examId).orElseThrow(()->new RuntimeException("Exam not found"));
        try{
            XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet=workbook.getSheetAt(0);
            List<Question> questions=new ArrayList<>();
            XSSFRow headerRow=sheet.getRow(0);
            for(Cell cell:headerRow){
               String columnName=cell.getStringCellValue();
               switch (columnName){
                   case "Section":
                       subjectIndex=cell.getColumnIndex();
                       break;
                   case "Question_Text":
                       questionTextIndex = cell.getColumnIndex();
                       break;
                   case "Option1":
                       option1Index = cell.getColumnIndex();
                       break;
                   case "Option2":
                       option2Index = cell.getColumnIndex();
                       break;
                   case "Option3":
                       option3Index = cell.getColumnIndex();
                       break;
                   case "Option4":
                       option4Index = cell.getColumnIndex();
                       break;
                   case "Correct_Answer(1-4)":
                       correctAnswerIndex = cell.getColumnIndex();
                       break;
                   case "Difficulty_Level":
                       difficultyIndex = cell.getColumnIndex();
                       break;



               }
            }
            for(int i=1;i<sheet.getLastRowNum();i++){
                XSSFRow row=sheet.getRow(i);
                if(row==null) continue;
                Question question=new Question();
                question.setSubject(row.getCell(subjectIndex).getStringCellValue());
                question.setQuestionText(row.getCell(questionTextIndex).getStringCellValue());
                question.setOption1(row.getCell(option1Index).toString());
                question.setOption2(row.getCell(option2Index).toString());
                question.setOption3(row.getCell(option3Index).toString());
                question.setOption4(row.getCell(option4Index).toString());

                // Correct answer as numeric
                int correctAnswer = (int) row.getCell(correctAnswerIndex).getNumericCellValue();
                question.setCorrectAnswer(correctAnswer);

                question.setDifficulty(row.getCell(difficultyIndex).getStringCellValue());

                questions.add(question);
            }
            workbook.close();
            questionRepository.saveAll(questions);
        }
        catch (IOException e) {
            throw new RuntimeException("Error while uploading excel file : "+e);
        }

    }
    @Override
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }



}
