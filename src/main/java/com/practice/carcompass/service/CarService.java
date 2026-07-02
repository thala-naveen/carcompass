package com.practice.carcompass.service;

import com.practice.carcompass.domain.Car;
import com.practice.carcompass.repository.CarRepository;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;
    private final GridFsTemplate gridFsTemplate;

    public CarService(CarRepository carRepository, GridFsTemplate gridFsTemplate) {
        this.carRepository = carRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    public List<Car> getTrendingCars() {
        // Sort by safetyRating + mileage as trending proxy; top 6
        return carRepository.findAll().stream()
                .sorted((a, b) -> {
                    int scoreA = (a.getSafetyRating() != null ? a.getSafetyRating() : 0) * 10 +
                                 (a.getMileage() != null ? a.getMileage().intValue() : 0);
                    int scoreB = (b.getSafetyRating() != null ? b.getSafetyRating() : 0) * 10 +
                                 (b.getMileage() != null ? b.getMileage().intValue() : 0);
                    return Integer.compare(scoreB, scoreA);
                })
                .limit(6)
                .collect(java.util.stream.Collectors.toList());
    }

    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    public Optional<Car> getCarById(String id) {
        return carRepository.findById(id);
    }

    public Car createCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(String id, Car carDetails) {
        return carRepository.findById(id).map(car -> {
            car.setMake(carDetails.getMake());
            car.setModel(carDetails.getModel());
            car.setVariant(carDetails.getVariant());
            car.setExShowroomPrice(carDetails.getExShowroomPrice());
            car.setSpecifications(carDetails.getSpecifications());
            car.setMileage(carDetails.getMileage());
            car.setSafetyRating(carDetails.getSafetyRating());
            car.setTags(carDetails.getTags());
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public void deleteCar(String id) {
        carRepository.deleteById(id);
    }

    public String uploadImage(MultipartFile file) throws Exception {
        ObjectId fileId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        return fileId.toString();
    }

    public GridFsResource downloadImage(String fileId) {
        try {
            org.bson.types.ObjectId objectId = new org.bson.types.ObjectId(fileId);
            GridFSFile gridFSFile = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(objectId)));
            if (gridFSFile != null) {
                return gridFsTemplate.getResource(gridFSFile);
            }
        } catch (IllegalArgumentException ignored) {
            // fileId is not a valid ObjectId string
        }
        return null;
    }
}
