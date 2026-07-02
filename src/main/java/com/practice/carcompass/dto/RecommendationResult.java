package com.practice.carcompass.dto;

import com.practice.carcompass.domain.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class RecommendationResult {
    private Car car;
    private int matchPercent;
    private List<String> matchedTags;
}
