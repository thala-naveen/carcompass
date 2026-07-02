package com.practice.carcompass.service;

import com.practice.carcompass.domain.Car;
import com.practice.carcompass.dto.AnswerRequest;
import com.practice.carcompass.dto.RecommendationResult;
import com.practice.carcompass.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Recommendation Engine – keyword-based matching (ignore case).
 *
 * Algorithm:
 * 1. Collect all selected option texts from the user's answers as keywords.
 * 2. For each car, count how many of its tags contain (or are contained by) any keyword.
 * 3. Calculate match percentage = (matches / total keywords) * 100, capped at 100.
 * 4. Sort descending by matchPercent, return all cars.
 */
@Service
public class RecommendationService {

    private final CarRepository carRepository;

    public RecommendationService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<RecommendationResult> recommend(AnswerRequest request) {
        // Step 1: Collect all keywords (the selected option texts)
        List<String> keywords = new ArrayList<>();
        if (request.getAnswers() != null) {
            for (AnswerRequest.QuestionAnswer answer : request.getAnswers()) {
                if (answer.getSelectedOptions() != null) {
                    keywords.addAll(answer.getSelectedOptions());
                }
            }
        }

        List<Car> cars = carRepository.findAll();
        List<RecommendationResult> results = new ArrayList<>();

        for (Car car : cars) {
            List<String> tags = car.getTags() != null ? car.getTags() : List.of();
            List<String> matchedTags = new ArrayList<>();
            int matchCount = 0;

            for (String keyword : keywords) {
                String kwLower = keyword.toLowerCase().trim();
                for (String tag : tags) {
                    String tagLower = tag.toLowerCase().trim();
                    // Keyword match: tag contains keyword OR keyword contains tag
                    if (tagLower.contains(kwLower) || kwLower.contains(tagLower)) {
                        matchCount++;
                        if (!matchedTags.contains(tag)) {
                            matchedTags.add(tag);
                        }
                        break; // count each keyword once per car
                    }
                }
            }

            int matchPercent = keywords.isEmpty() ? 0
                    : (int) Math.min(100, Math.round((matchCount * 100.0) / keywords.size()));

            results.add(new RecommendationResult(car, matchPercent, matchedTags));
        }

        // Sort descending by match percentage
        results.sort(Comparator.comparingInt(RecommendationResult::getMatchPercent).reversed());

        return results;
    }
}
