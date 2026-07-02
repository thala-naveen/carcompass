#!/bin/bash

# Exit immediately if a command exits with a non-zero status
set -e

echo "======================================"
echo "Starting MongoDB via Docker Compose..."
echo "======================================"
docker-compose up -d

echo ""
echo "======================================"
echo "Building the Spring Boot application..."
echo "======================================"
./mvnw clean package -DskipTests

echo ""
echo "======================================"
echo "Running the application..."
echo "======================================"
java -jar target/carcompass-0.0.1-SNAPSHOT.jar
