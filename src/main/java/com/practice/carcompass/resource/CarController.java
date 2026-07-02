package com.practice.carcompass.resource;

import com.practice.carcompass.domain.Car;
import com.practice.carcompass.service.CarService;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/trending")
    public List<Car> getTrendingCars() {
        // Returns top 6 cars sorted by safety rating + mileage as a proxy for trending
        return carService.getTrendingCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable String id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.createCar(car));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> updateCar(@PathVariable String id, @RequestBody Car car) {
        try {
            return ResponseEntity.ok(carService.updateCar(id, car));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCar(@PathVariable String id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Car> uploadCarImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            String fileId = carService.uploadImage(file);
            Car car = carService.getCarById(id).orElseThrow(() -> new RuntimeException("Car not found"));
            if (car.getImageGridFsIds() == null) {
                car.setImageGridFsIds(new ArrayList<>());
            }
            car.getImageGridFsIds().add(fileId);
            return ResponseEntity.ok(carService.createCar(car));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/images/{fileId}")
    public ResponseEntity<org.springframework.core.io.InputStreamResource> downloadImage(@PathVariable String fileId) {
        GridFsResource resource = carService.downloadImage(fileId);
        if (resource == null || !resource.exists()) {
            return ResponseEntity.notFound().build();
        }
        try {
            // Detect content type from stored GridFS metadata
            String contentType = resource.getContentType();
            if (contentType == null || contentType.isBlank()) {
                contentType = "image/jpeg";
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CACHE_CONTROL, "max-age=86400")
                    .body(new org.springframework.core.io.InputStreamResource(resource.getInputStream()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
