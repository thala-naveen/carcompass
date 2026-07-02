package com.practice.carcompass.resource;

import com.practice.carcompass.dto.AnswerRequest;
import com.practice.carcompass.dto.RecommendationResult;
import com.practice.carcompass.service.RecommendationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationController {

    private final RecommendationService recommendationService;

    public RecommendationController(RecommendationService recommendationService) {
        this.recommendationService = recommendationService;
    }

    @PostMapping
    public ResponseEntity<List<RecommendationResult>> getRecommendations(@RequestBody AnswerRequest request) {
        List<RecommendationResult> results = recommendationService.recommend(request);
        return ResponseEntity.ok(results);
    }
}
