package com.exam.portal.service;

import com.exam.portal.exception.ExamPortalException;
import com.exam.portal.model.Category;
import com.exam.portal.model.Quiz;
import com.exam.portal.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public void addQuiz(Quiz quiz) {
        quizRepository.save(quiz);
    }

    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    public Quiz getQuiz(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new ExamPortalException("No Quiz is found"));
    }

    public ResponseEntity<Quiz> deleteQuiz(Long id) {
        Quiz quiz = getQuiz(id);
        quizRepository.delete(quiz);
        return new ResponseEntity<>(quiz, OK);
    }
}
