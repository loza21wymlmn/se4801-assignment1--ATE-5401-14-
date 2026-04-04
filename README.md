# shopwave starter - C1 project setup
## Student Information
### Name -Loza Wondwossen
### Student number - ATE/5401/14


## Project Description
### This is the intial setup of the shopwave starter project using 
### # Spring boot 3.5.12, maven and java21 
### At this stage, the project contains only pom.xml and configuration to run the application.
## How to Build
### mvn clean install
## How to Run
### mvn spring-boot:run
## Application Configuration
### The application is configured using application.properties.

# C2 — Domain Model

### In this stage, the domain model of the application was implemented using JPA and Hibernate.

## Entities created:
### Category

### Product

### Order

### OrderItem

# Relationships:
#### - Category -> Product (One-to-Many)
#### - Order -> OrderItem (One-to-Many with orphanRemoval)
#### - OrderItem -> Product (Many-to-One)

# Key Features:
#### Use of @Entity, @Table, and JPA annotations

#### Proper relationship mapping using @ManyToOne and @OneToMany

#### Automatic timestamping using @CreationTimestamp

#### Use of BigDecimal for price handling

#### Enum (OrderStatus) for order state management

# Additional Implementation:
#### Implemented addItem(Product, int quantity) method in Order class to manage order items

# C3 - Repository and Service

This module handles all product operations: creating, retrieving, searching, and updating products in the ShopWave application.

---

## Key Features

- Create Product: Add new products with name, description, price, stock, and category.
- Get All Products: Paginated list of products.
- Get Product by ID: Fetch a product by ID; throws ProductNotFoundException if not found.
- Search Products: Filter products by name and/or maximum price.
- Update Stock: Adjust stock up or down; prevents negative stock.

---

## Technical Details

- Repository: ProductRepository extends JpaRepository<Product, Long> with custom queries:
    - findByCategoryId(Long categoryId)
    - findByPriceLessThanEqual(BigDecimal maxPrice)
    - findByNameContainingIgnoreCase(String keyword)
    - findTopByOrderByPriceDesc()

- Service: ProductService annotated with @Service and @Transactional.
    - Read-only methods use @Transactional(readOnly = true).
    - Converts entities to ProductDTO via ProductMapper.

- Exceptions:
    - ProductNotFoundException for missing products.
    - IllegalArgumentException if stock would become negative.

---

## Why It Matters

This service keeps all product logic centralized, ensures safe stock handling, and provides clean DTOs for easy use in APIs or frontends.



# C4– Product REST API (C4)

This module exposes REST endpoints for managing products in ShopWave, including listing, creating, searching, and updating stock.

---

## Endpoints

- GET /api/products – Paginated list of products (`page` & size optional)
- GET /api/products/{id} – Get a product by ID (404 if not found)
- POST /api/products – Create a product (`CreateProductRequest` JSON, validated)
- GET /api/products/search – Search by keyword and/or maxPrice (both optional)
- PATCH /api/products/{id}/stock – Update stock with { "delta": 5 } (negative allowed, cannot go below 0)

---

## Error Handling

All errors return JSON with:

- timestamp – when the error occurred
- status – HTTP status code
- error – short error description
- message – detailed message
- path – the requested URI

Handled globally via @RestControllerAdvice:

- ProductNotFoundException → 404 Not Found
- Validation errors (`@Valid`) → 400 Bad Request
- Illegal stock updates → 400 Bad Request

---

## Notes

- Uses @RestController, @RequestMapping("/api"), and proper HTTP status codes.
- ProductService handles all business logic and DTO conversion.
- Validations ensure safe and consistent API inputs.


# C5: ProductController Unit Tests

This module tests the ProductController endpoints using Spring WebMvcTest and MockMvc.

## Features Tested

1. GET /api/products
  - Returns a list of products.
  - Verifies product data in JSON response.

2. GET /api/products/{id} (Not Found)
  - Returns 404 when the product ID does not exist.
  - Checks that the error message matches GlobalExceptionHandler.

## Implementation Notes

- @WebMvcTest(ProductController.class) loads only the controller layer.
- @MockBean is used for ProductService to isolate the controller.
- @Import(GlobalExceptionHandler.class) includes exception handling.
- MockMvc is used to simulate HTTP requests and verify JSON responses.
- JSON assertions match the actual output (`List<ProductDTO>`).

## How to Run Tests

Run tests using Maven:




# Project Summary

This project is a **Spring Boot-based backend application for ShopWave**, developed as part of the SE4801 Enterprise Application Development assignment. It implements a layered architecture with domain modeling, repository and service layers, RESTful APIs, and testing.

The system supports core functionalities such as:

* Product and category management
* Searching and filtering products
* Stock updates with validation
* Order and order item relationships
* REST API endpoints with proper error handling

The project follows **best practices in Spring Boot**, including DTO mapping, transactional service logic, and structured exception handling.


## Testing & Bonus Implementation

The application includes:

* Unit testing using Mockito
* Controller testing using `@WebMvcTest`
* Repository testing

For the bonus task, repository tests were enhanced using **Testcontainers with PostgreSQL**, replacing the default in-memory database. This ensures more realistic and isolated test environments.



## AI Assistance Disclosure

AI tools (such as ChatGPT) were used in a limited and responsible manner during this project. Specifically, AI was used to:

* Assist in understanding and configuring Testcontainers for the bonus task
* Provide guidance on structuring test classes and resolving minor errors
* Support Git command usage and debugging during development

All generated suggestions were carefully reviewed, tested, and adapted. The final implementation, design decisions, and validation of functionality were completed independently by the developer.



## Conclusion

This project demonstrates the development of a **structured, testable, and maintainable Spring Boot backend**, applying enterprise development principles and modern testing practices.
