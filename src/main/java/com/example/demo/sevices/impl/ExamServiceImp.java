package com.example.demo.sevices.impl;

import com.example.demo.entity.Exam;
import com.example.demo.entity.Question;
import com.example.demo.repository.ExamRepository;
import com.example.demo.repository.QuestionRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
        // 1️⃣ Save exam first
        Exam savedExam = examRepository.save(exam);
        List<Question> questions=new ArrayList<>();
        int difficultyIndex=-1;
        int option1Index=-1;
        int option2Index=-1;
        int option3Index=-1;
        int option4Index=-1;
        int subjectIndex=-1;
        int questionTextIndex=-1;
        int correctAnswerIndex=-1;
        int reviewIndex=-1;
        try {
            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow headerRow = sheet.getRow(0);

            for (Cell cell : headerRow) {
                String columnName = cell.getStringCellValue();
                switch (columnName) {
                    case "Section":
                        subjectIndex = cell.getColumnIndex();
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
                    case "Review/Explanation" :
                        reviewIndex=cell.getColumnIndex();
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
                String correctAnswer = row.getCell(correctAnswerIndex).getStringCellValue();
                question.setCorrectAnswer(correctAnswer);

                question.setDifficulty(row.getCell(difficultyIndex).getStringCellValue());
question.setReview(row.getCell(reviewIndex).getStringCellValue());
                question.setExam(savedExam);
                questions.add(question);
            }
            questionRepository.saveAll(questions);
        }catch (Exception e) {
            throw new RuntimeException("error uploading exam"+e);
        }
       // exam.setQuestionList(questions);
       // examRepository.save(exam);
      //  return questions;
    }

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
}
