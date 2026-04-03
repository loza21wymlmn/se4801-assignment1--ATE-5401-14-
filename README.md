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