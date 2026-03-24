//package com.example.demo.helper;
//
//import com.example.demo.entity.Question;
//
//import java.util.*;
//import java.util.regex.*;
//
//public class PdfParser {
//
//    public static List<Question> parse(String text) {
//
//        List<Question> questions = new ArrayList<>();
//        Map<Integer, String> answerMap = extractAnswers(text);
//
//        Pattern qPattern = Pattern.compile(
//                "(\\d+)\\.\\s+(.*?)(?=(\\d+\\.|Answer key))",
//                Pattern.DOTALL
//        );
//
//        Matcher matcher = qPattern.matcher(text);
//
//        while (matcher.find()) {
//
//            int qNo = Integer.parseInt(matcher.group(1));
//            String block = matcher.group(2);
//
//            Question q = new Question();
//            q.setQuestionNumber(qNo);
//
//            // Question text
//            String questionText = block.split("a\\)")[0];
//            q.setQuestionText(questionText.trim());
//
//            // Options
//            Pattern optPattern = Pattern.compile(
//                    "a\\)(.*?)b\\)(.*?)c\\)(.*?)d\\)(.*)",
//                    Pattern.DOTALL
//            );
//
//            Matcher optMatcher = optPattern.matcher(block);
//
//            if (optMatcher.find()) {
//                q.setOption1(optMatcher.group(1).trim());
//                q.setOption2(optMatcher.group(2).trim());
//                q.setOption3(optMatcher.group(3).trim());
//                q.setOption4(optMatcher.group(4).trim());
//            }
//
//            // Answer
//            q.setCorrectAnswer(answerMap.getOrDefault(qNo, "N/A"));
//
//            // Category (simple logic)
//            q.setSubject("General");
//
//            questions.add(q);
//        }
//
//        return questions;
//    }
//
//    private static Map<Integer, String> extractAnswers(String text) {
//
//        Map<Integer, String> map = new HashMap<>();
//
//        Pattern ansPattern = Pattern.compile("(\\d+)\\.\\s*([a-d])");
//        Matcher matcher = ansPattern.matcher(text);
//
//        while (matcher.find()) {
//            map.put(Integer.parseInt(matcher.group(1)), matcher.group(2));
//        }
//
//        return map;
//    }
//}
