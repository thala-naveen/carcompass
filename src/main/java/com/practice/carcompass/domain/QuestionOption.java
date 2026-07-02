package com.practice.carcompass.domain;

import lombok.Data;
import java.util.Map;

@Data
public class QuestionOption {
    private String text;
    private Map<String, Integer> weightMappings; // Map of Tag -> Weight
}
