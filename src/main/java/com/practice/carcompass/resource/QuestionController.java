package com.practice.carcompass.resource;

import com.practice.carcompass.domain.Question;
import com.practice.carcompass.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionRepository questionRepository;

    public QuestionController(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @GetMapping
    public List<Question> getActiveQuestions() {
        return questionRepository.findByIsActiveTrueOrderByOrderAsc();
    }

    @GetMapping("/all")
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Question> getQuestionById(@PathVariable String id) {
        return questionRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Question> createQuestion(@RequestBody Question question) {
        return ResponseEntity.status(HttpStatus.CREATED).body(questionRepository.save(question));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Question> updateQuestion(@PathVariable String id, @RequestBody Question questionDetails) {
        return questionRepository.findById(id).map(question -> {
            question.setText(questionDetails.getText());
            question.setType(questionDetails.getType());
            question.setOptions(questionDetails.getOptions());
            question.setOrder(questionDetails.getOrder());
            question.setIsActive(questionDetails.getIsActive());
            return ResponseEntity.ok(questionRepository.save(question));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteQuestion(@PathVariable String id) {
        questionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
