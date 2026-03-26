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