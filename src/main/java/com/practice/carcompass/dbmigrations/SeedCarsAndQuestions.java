package com.practice.carcompass.dbmigrations;

import com.practice.carcompass.domain.Car;
import com.practice.carcompass.domain.Question;
import com.practice.carcompass.domain.QuestionOption;
import com.practice.carcompass.domain.QuestionType;
import com.practice.carcompass.repository.CarRepository;
import com.practice.carcompass.repository.QuestionRepository;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import io.mongock.api.annotations.RollbackExecution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ChangeUnit(id = "seed-cars-and-questions", order = "002", author = "system")
public class SeedCarsAndQuestions {

    @Execution
    public void seedData(CarRepository carRepository, QuestionRepository questionRepository) {
        // Seed default questions if none exist
        if (questionRepository.count() == 0) {
            seedQuestions(questionRepository);
        }

        // Seed some sample cars if none exist
        if (carRepository.count() == 0) {
            seedCars(carRepository);
        }
    }

    private void seedQuestions(QuestionRepository questionRepository) {
        List<Question> questions = new ArrayList<>();

        // Question 1
        Question q1 = new Question();
        q1.setText("What is your budget?");
        q1.setType(QuestionType.SINGLE_CHOICE);
        q1.setOrder(1);
        q1.setIsActive(true);
        
        List<QuestionOption> q1Options = new ArrayList<>();
        q1Options.add(createOption("Under $20,000", Map.of("budget_low", 10)));
        q1Options.add(createOption("$20,000 - $40,000", Map.of("budget_mid", 10)));
        q1Options.add(createOption("Over $40,000", Map.of("budget_high", 10)));
        q1.setOptions(q1Options);
        questions.add(q1);

        // Question 2
        Question q2 = new Question();
        q2.setText("What type of car do you prefer?");
        q2.setType(QuestionType.MULTIPLE_CHOICE);
        q2.setOrder(2);
        q2.setIsActive(true);

        List<QuestionOption> q2Options = new ArrayList<>();
        q2Options.add(createOption("SUV", Map.of("suv", 10)));
        q2Options.add(createOption("Sedan", Map.of("sedan", 10)));
        q2Options.add(createOption("Hatchback", Map.of("hatchback", 10)));
        q2.setOptions(q2Options);
        questions.add(q2);

        questionRepository.saveAll(questions);
    }

    private void seedCars(CarRepository carRepository) {
        List<Car> cars = new ArrayList<>();

        Car car1 = new Car();
        car1.setMake("Toyota");
        car1.setModel("Corolla");
        car1.setVariant("LE");
        car1.setExShowroomPrice(22000.0);
        car1.setMileage(30.0);
        car1.setSafetyRating(5);
        car1.setTags(List.of("sedan", "budget_low", "reliable"));
        cars.add(car1);

        Car car2 = new Car();
        car2.setMake("Honda");
        car2.setModel("CR-V");
        car2.setVariant("EX");
        car2.setExShowroomPrice(32000.0);
        car2.setMileage(28.0);
        car2.setSafetyRating(5);
        car2.setTags(List.of("suv", "budget_mid", "family"));
        cars.add(car2);

        carRepository.saveAll(cars);
    }

    private QuestionOption createOption(String text, Map<String, Integer> weights) {
        QuestionOption option = new QuestionOption();
        option.setText(text);
        option.setWeightMappings(new HashMap<>(weights));
        return option;
    }

    @RollbackExecution
    public void rollback(CarRepository carRepository, QuestionRepository questionRepository) {
        // Rollback logic
    }
}
