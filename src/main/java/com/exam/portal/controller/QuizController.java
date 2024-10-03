package com.exam.portal.controller;

import com.exam.portal.model.Quiz;
import com.exam.portal.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService questionService;

    @PostMapping("/")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz) {
        questionService.addQuiz(quiz);
        return new ResponseEntity<>(quiz, OK);
    }

    @GetMapping("/")
    public List<Quiz> getQuiz() {
        return questionService.getAll();
    }

    @GetMapping("/{id}")
    public Quiz getQuiz(@PathVariable Long id) {
        return questionService.getQuiz(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Quiz> deleteQuiz(@PathVariable Long id) {
        return questionService.deleteQuiz(id);
    }
}
