package com.practice.carcompass.dto;

import lombok.Data;
import java.util.List;

@Data
public class AnswerRequest {
    private List<QuestionAnswer> answers;

    @Data
    public static class QuestionAnswer {
        private String questionId;
        private List<String> selectedOptions;
    }
}
