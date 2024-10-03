package com.exam.portal.service;

import com.exam.portal.exception.ExamPortalException;
import com.exam.portal.model.Category;
import com.exam.portal.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }
    public Category getCategory(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ExamPortalException("No Category exit with " + id));
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public ResponseEntity<Category> deleteCategory(Long id) {
        Category category = getCategory(id);
        categoryRepository.delete(category);
        return new ResponseEntity<>(category, OK);
    }

}
