# Product Management API

A Spring Boot REST API for Product Management with JWT Authentication, Role-Based Authorization, PostgreSQL, and Docker support.

## Quick Start

### Run with Docker (Recommended)

```bash
docker compose up -d --build
```

Application:

```text
http://localhost:8080
```

### Run Locally

```bash
mvn clean install
mvn spring-boot:run
```

Application:

```text
http://localhost:8080
```

---

## Features

### Authentication & Authorization

* JWT-based authentication
* Role-based access control
* Stateless security using Spring Security
* Secure password hashing using BCrypt

### Product Management

* Create Product
* Get Product by ID
* Get All Products
* Update Product
* Delete Product

### Validation

* Request validation using Jakarta Validation
* Global exception handling
* Standardized API error responses

### Testing

* Unit testing with JUnit 5
* Mockito for mocking dependencies
* MockMvc for controller testing

### Docker Support

* Dockerized Spring Boot application
* PostgreSQL container
* One-command startup using Docker Compose

---

# Technology Stack

| Technology      | Version |
| --------------- | ------- |
| Java            | 21      |
| Spring Boot     | 3.x     |
| Spring Security | 6.x     |
| Spring Data JPA | 3.x     |
| PostgreSQL      | 16      |
| JWT (JJWT)      | Latest  |
| Lombok          | Latest  |
| Maven           | 3.x     |
| Docker          | Latest  |
| Docker Compose  | Latest  |
| JUnit 5         | Latest  |
| Mockito         | Latest  |

---

# Project Structure

```text
product-management
│
├── Dockerfile
├── docker-compose.yml
├── .dockerignore
├── pom.xml
├── README.md
│
src
├── main
│   ├── java
│   │   └── styles_ai_task
│   │       └── product_management
│   │
│   │           ├── config
│   │           │   ├── SecurityConfig.java
│   │           │   └── DataSeeder.java
│   │           │
│   │           ├── controller
│   │           │   ├── AuthController.java
│   │           │   └── ProductController.java
│   │           │
│   │           ├── dto
│   │           │   ├── LoginRequest.java
│   │           │   ├── LoginResponse.java
│   │           │   ├── ProductRequest.java
│   │           │   └── ProductResponse.java
│   │           │
│   │           ├── entity
│   │           │   ├── Product.java
│   │           │   └── User.java
│   │           │
│   │           ├── exception
│   │           │   ├── BusinessException.java
│   │           │   ├── ErrorCodes.java
│   │           │   ├── ErrorResponse.java
│   │           │   └── GlobalExceptionHandler.java
│   │           │
│   │           ├── repository
│   │           │   ├── ProductRepository.java
│   │           │   └── UserRepository.java
│   │           │
│   │           ├── security
│   │           │   ├── JwtAuthenticationFilter.java
│   │           │   └── JwtService.java
│   │           │
│   │           ├── service
│   │           │   ├── AuthService.java
│   │           │   └── ProductService.java
│   │           │
│   │           └── ProductManagementApplication.java
│   │
│   └── resources
│       └── application.properties
│
└── test
    └── java
        └── styles_ai_task
            └── product_management
                ├── controller
                │   └── ProductControllerTest.java
                ├── exception
                │   └── GlobalExceptionHandlerTest.java
                ├── service
                │   ├── AuthServiceTest.java
                │   └── ProductServiceTest.java
                └── ProductManagementApplicationTests.java
```

---

# Roles & Permissions

| Role | Permission                            |
| ---- | ------------------------------------- |
| VIEW | Read Products                         |
| EDIT | Read, Create, Update, Delete Products |

---

# Running the Application

## Prerequisites

Install:

* Java 21
* Maven 3.9+
* PostgreSQL 16

Verify installation:

```bash
java -version
mvn -version
```

---

## Clone Repository

```bash
git clone https://github.com/umeshnamdev234/product-management.git
cd product-management
```

---

## Build Project

```bash
mvn clean install
```

---

## Run Application

```bash
mvn spring-boot:run
```

or

```bash
java -jar target/product-management-*.jar
```

Application starts on:

```text
http://localhost:8080
```

---

# Docker Setup

## Prerequisites

Install:

* Docker
* Docker Compose

Verify installation:

```bash
docker --version
docker compose version
```

---

## Run Application with Docker

Build and start PostgreSQL and Spring Boot application:

```bash
docker compose up --build
```

Run in background:

```bash
docker compose up -d --build
```

Application:

```text
http://localhost:8080
```

Database:

```text
Host: localhost
Port: 5432
Database: product_management
Username: postgres
Password: postgres
```

---

## Stop Containers

```bash
docker compose down
```

Remove containers and database volume:

```bash
docker compose down -v
```

---

## View Logs

Application logs:

```bash
docker compose logs -f app
```

Database logs:

```bash
docker compose logs -f postgres
```

---

# Default Seeded Users

The application automatically creates default users during startup if they do not already exist.

| Username | Password  | Role |
| -------- | --------- | ---- |
| admin    | admin123  | EDIT |
| viewer   | viewer123 | VIEW |

### EDIT

* View Products
* Create Products
* Update Products
* Delete Products

### VIEW

* View Products only

---

# Authentication

## Login as Admin

```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
  "username": "admin",
  "password": "admin123"
}'
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

Save token:

```bash
TOKEN=<JWT_TOKEN>
```

---

## Login as Viewer

```bash
curl --location 'http://localhost:8080/auth/login' \
--header 'Content-Type: application/json' \
--data '{
  "username": "viewer",
  "password": "viewer123"
}'
```

---

# Product APIs

## Create Product (EDIT Role)

```bash
curl --location 'http://localhost:8080/products' \
--header "Authorization: Bearer $TOKEN" \
--header 'Content-Type: application/json' \
--data '{
  "name": "Men Shirt",
  "description": "Cotton Shirt",
  "price": 499.99,
  "quantity": 20,
  "imageUrl": "https://example.com/shirt.jpg"
}'
```

---

## Get All Products (VIEW / EDIT Role)

```bash
curl --location 'http://localhost:8080/products?pageNumber=2&pageSize=5' \
--header "Authorization: Bearer $TOKEN"
```
--

## Get Product By ID (VIEW / EDIT Role)

```bash
curl --location 'http://localhost:8080/products/{productId}' \
--header "Authorization: Bearer $TOKEN"
```

---

## Update Product (EDIT Role)

```bash
curl --location --request PUT 'http://localhost:8080/products/{productId}' \
--header "Authorization: Bearer $TOKEN" \
--header 'Content-Type: application/json' \
--data '{
  "name": "Updated Shirt",
  "description": "Premium Cotton Shirt",
  "price": 699.99,
  "quantity": 15,
  "imageUrl": "https://example.com/updated-shirt.jpg"
}'
```

---

## Delete Product (EDIT Role)

```bash
curl --location --request DELETE 'http://localhost:8080/products/{productId}' \
--header "Authorization: Bearer $TOKEN"
```

---

# Running Tests

## Run Tests Locally

Run all tests:

```bash
mvn test
```

Run a specific test:

```bash
mvn test -Dtest=ProductServiceTest
```

Run controller tests:

```bash
mvn test -Dtest=ProductControllerTest
```

Generate detailed logs:

```bash
mvn test -X
```

---

## Run Tests Inside Docker

Run all tests:

```bash
docker run --rm -v "$(pwd)":/app -w /app maven:3.9.9-eclipse-temurin-21 mvn test
```

Run specific tests:

```bash
docker run --rm -v "$(pwd)":/app -w /app maven:3.9.9-eclipse-temurin-21 mvn test -Dtest=ProductServiceTest
```

---

## Build and Verify

Local:

```bash
mvn clean verify
```

Docker:

```bash
docker run --rm -v "$(pwd)":/app -w /app maven:3.9.9-eclipse-temurin-21 mvn clean verify
```

---

# Error Response Format

```json
{
  "code": "PRODUCT_NOT_FOUND",
  "message": "Product not found"
}
```

Validation example:

```json
{
  "code": "INVALID_REQUEST",
  "message": "Validation failed",
  "errors": {
    "name": "Product name is required"
  }
}
```

---

# Author

Umesh Namdev

Backend Engineer | Java | Spring Boot | Security | REST APIs
