# Product Management API

A Spring Boot REST API for Product Management with JWT Authentication and Role-Based Authorization.

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

---

# Technology Stack

| Technology      | Version |
| --------------- | ------- |
| Java            | 21      |
| Spring Boot     | 3.x     |
| Spring Security | 6.x     |
| Spring Data JPA | 3.x     |
| H2 Database     | Latest  |
| JWT (JJWT)      | Latest  |
| Lombok          | Latest  |
| Maven           | 3.x     |
| JUnit 5         | Latest  |
| Mockito         | Latest  |

---

# Project Structure

```text
src
├── main
│   ├── java
│   │   └── styles_ai_task
│   │       └── product_management
│   │
│   │           ├── config
│   │           │   └── SecurityConfig.java
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
                │
                ├── exception
                │   └── GlobalExceptionHandlerTest.java
                │
                ├── service
                │   ├── AuthServiceTest.java
                │   └── ProductServiceTest.java
                │
                └── ProductManagementApplicationTests.java
```

---

# Roles & Permissions

| Role | Permission                            |
| ---- | ------------------------------------- |
| VIEW | Read Products                         |
| EDIT | Read, Create, Update, Delete Products |

### Product APIs

| Endpoint              | Permission  |
| --------------------- | ----------- |
| GET /products         | VIEW / EDIT |
| GET /products/{id}    | VIEW / EDIT |
| POST /products        | EDIT        |
| PUT /products/{id}    | EDIT        |
| DELETE /products/{id} | EDIT        |

---

# Running the Application

## Prerequisites

Install:

* Java 21
* Maven 3.9+

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

Successful build:

```text
BUILD SUCCESS
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

# Authentication

## Login Request

```http
POST /auth/login
```

Request:

```json
{
  "username": "admin",
  "password": "admin123"
}
```

Response:

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

# Using JWT Token

Include JWT token in Authorization header:

```http
Authorization: Bearer <JWT_TOKEN>
```

Example:

```http
GET /products
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

# Product APIs

## Create Product

```http
POST /products
```

Request:

```json
{
  "name": "Men Shirt",
  "description": "Cotton Shirt",
  "price": 499.99,
  "quantity": 20,
  "imageUrl": "https://example.com/image.jpg"
}
```

---

## Get All Products

```http
GET /products
```

---

## Get Product By Id

```http
GET /products/{id}
```

---

## Update Product

```http
PUT /products/{id}
```

---

## Delete Product

```http
DELETE /products/{id}
```

---

# Running Tests

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

Generate detailed test logs:

```bash
mvn test -X
```

---

# Test Coverage

The project includes tests for:

### Service Layer

* ProductService
* AuthService

### Controller Layer

* ProductController

### Exception Handling

* GlobalExceptionHandler

### Application Context

* Spring Boot startup validation

---

# Error Response Format

Example:

```json
{
  "code": "PRODUCT_NOT_FOUND",
  "message": "Product not found"
}
```

Validation Example:

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

# Security Flow

```text
Client
   |
   | Login
   v
AuthController
   |
   v
AuthService
   |
   v
JWT Token Generated
   |
   v
Client sends JWT
   |
   v
JwtAuthenticationFilter
   |
   v
SecurityContext
   |
   v
Protected APIs
```

---

# Future Improvements

* Refresh Token Support
* Swagger / OpenAPI Documentation
* Docker Support
* Pagination & Sorting
* Product Search API
* Audit Logging
* PostgreSQL/MySQL Integration
* Integration Tests
* Test Coverage Reporting

---

# Author

Umesh Namdev

Backend Engineer | Java | Spring Boot | Security | REST APIs
