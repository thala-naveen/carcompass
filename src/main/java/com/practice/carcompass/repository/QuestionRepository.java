package com.practice.carcompass.repository;

import com.practice.carcompass.domain.Question;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends MongoRepository<Question, String> {
    List<Question> findByIsActiveTrueOrderByOrderAsc();
}
