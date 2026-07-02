# Car Recommendation System PRD

## Goal

Build a web application that helps users choose the most suitable car by
asking a questionnaire and ranking cars by match percentage.

## Features

### Car Catalogue

-   Makes, models, variants
-   Ex-showroom prices
-   Specifications
-   Mileage
-   Safety ratings
-   Images
-   User ratings & reviews

### Recommendation Engine

-   Dynamic questionnaire
-   Weighted scoring
-   Ranked recommendations
-   Explain why each car matches

### Trending Cars

-   Based on average rating
-   Number of reviews
-   Recent popularity

## Roles

### Admin

#### Car Management

-   CRUD cars
-   Upload images to MongoDB GridFS

#### User Management

-   View users
-   Seed default admin/users via Mongock (`users.json`)

#### Questionnaire Management

-   CRUD questions
-   Reorder questions
-   Enable/Disable questions
-   Seed default questions via Mongock

Default questions: 1. What is your budget? 2. Is this your first car? 3.
How many family members usually travel? 4. What is your average daily
driving? 5. Where do you drive the most? 6. What type of car do you
prefer? 7. Preferred fuel type? 8. Preferred transmission? 9. Which
features are must-haves? 10. What matters most? - Safety - Mileage - Low
maintenance - Comfort - Performance - Features - Resale value - Brand
reputation - Spacious cabin

### User

-   Sign up
-   Login / Logout
-   Browse cars
-   View specifications
-   Rate cars
-   Write reviews
-   View recommendations

## Landing Page

Question: \> Are you confused about which car to buy?

Buttons: - Yes, help me choose - No, I know what I want

### Help Me Choose

-   One question per page
-   Progress indicator
-   Dynamic questions
-   Paginated flow
-   Calculate match percentage
-   Display ranked cars with explanation

### Browse

-   Trending cars
-   Search
-   Filters
-   Sorting

## Backend

-   Spring Boot
-   Spring Security
-   Spring Data MongoDB
-   MongoDB GridFS
-   Mongock
-   BCrypt password hashing

## MongoDB Collections

-   Users
-   Cars
-   Questions
-   Reviews
-   RecommendationSessions (optional)

## Database Initialization

Seed using Mongock: - users.json - cars.json - questions.json

## MVP Roadmap

1.  Authentication
2.  Car CRUD
3.  Questionnaire CRUD
4.  Recommendation Engine
5.  Ratings & Reviews
6.  Trending Cars

## Future Enhancements

-   AI recommendations
-   Compare cars
-   Wishlist
-   EMI calculator
-   Dealer locator
-   Test drive booking
-   Ownership cost estimation
