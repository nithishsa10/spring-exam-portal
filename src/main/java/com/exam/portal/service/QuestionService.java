package com.exam.portal.service;

import com.exam.portal.exception.ExamPortalException;
import com.exam.portal.model.Question;
import com.exam.portal.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final QuestionRepository questionRepository;

    public void addQuestion(Question question) {
        questionRepository.save(question);
    }

    public List<Question> getQuestion() {
        return questionRepository.findAll();
    }

    public Question getQuestion(Long id) {
        return questionRepository.findById(id)
                .orElseThrow(() -> new ExamPortalException("Question is not found"));
    }

    public ResponseEntity<Question> deleteQuestion(Long id) {
        Question question = getQuestion(id);
        questionRepository.delete(question);
        return new ResponseEntity<>(question, OK);
    }
}
