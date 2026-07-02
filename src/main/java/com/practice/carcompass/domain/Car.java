package com.practice.carcompass.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@Document(collection = "cars")
public class Car {
    @Id
    private String id;
    
    private String make;
    private String model;
    private String variant;
    
    private Double exShowroomPrice;
    private Map<String, String> specifications;
    private Double mileage;
    private Integer safetyRating;
    
    private List<String> imageGridFsIds;
    private List<String> tags;
}
