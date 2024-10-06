# Spring Boot REST API Service

This is a Spring Boot-based REST API service built to manage multiple entities such as Users, Information, and Persons. It integrates with a MySQL database using JPA, provides JWT-based authentication, and utilizes Liquibase for database versioning. The project contains both secure and non-secure endpoints, some of which require JWT authentication.

## Features

- **User Management**
  - Register, retrieve, update, and delete users.
  - Authentication with JWT.
  - Role-based access control.
- **Information Management**
  - Add, retrieve, update, and delete information records.
- **Person Management**
  - Add, retrieve, update, and delete person records.
- **Secure and Non-Secure Endpoints**
  - Public and private routes protected by JWT tokens.
- **Database Integration**
  - MySQL integration with Spring Data JPA.
  - Liquibase for database migrations.

## Technologies Used

- **Java 17**
- **Spring Boot 3.3.4**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA**
- **MySQL**
- **Liquibase**
- **Lombok**
- **JUnit 5 for Testing**

## Requirements

- Java 17
- Maven
- MySQL
- Postman (for testing API)

## Getting Started

### Clone the repository

```bash
git clone <repository-url>
cd <repository-directory>
```

### Configure MySQL

1. Set up a MySQL database and update the connection details in `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
spring.datasource.username=your_username
spring.datasource.password=your_password
```

2. Run the following command to apply Liquibase migrations and set up the database schema:

```bash
./mvnw liquibase:update
```

### Run the Application

To start the Spring Boot application:

```bash
./mvnw spring-boot:run
```

### Accessing the API

The API is available at: `http://localhost:8888/api/v1/`

Use Postman to test the API by importing the collection provided in `SpringBoot Rest API Service.postman_collection.json`.

### Key Modules & Endpoints

#### **User Management**
- **Register User**: `POST /api/v1/users/register`
  - Request Body Example:
    ```json
    {
      "username": "admin",
      "password": "admin@1234",
      "role": "ADMIN"
    }
    ```
- **Get User**: `GET /api/v1/users/{id}`
- **Edit User**: `PUT /api/v1/users/{id}`
- **Delete User**: `DELETE /api/v1/users/{id}`

#### **Information Management**
- **Add Information**: `POST /api/v1/info`
  - Request Body Example:
    ```json
    {
      "name": "Basic Info"
    }
    ```
- **Get Information**: `GET /api/v1/info/{id}`
- **Edit Information**: `PUT /api/v1/info/{id}`
- **Delete Information**: `DELETE /api/v1/info/{id}`

#### **Person Management**
- **Add Person**: `POST /api/v1/persons`
  - Request Body Example:
    ```json
    {
      "userId": 1,
      "firstName": "Md. Mosfikur",
      "lastName": "Rahman"
    }
    ```
- **Get Person**: `GET /api/v1/persons/{id}`
- **Edit Person**: `PUT /api/v1/persons/{id}`
- **Delete Person**: `DELETE /api/v1/persons/{id}`

#### **Authentication**
- **Login**: `POST /api/v1/login`
  - Request Body Example:
    ```json
    {
      "username": "admin",
      "password": "admin@1234"
    }
    ```
- **Logout**: `POST /api/v1/logout` (JWT token required)

#### **Secure Endpoints**
- **Base**: `GET /api/v1/secure` (JWT token required)
- **Status**: `GET /api/v1/secure/status` (JWT token required)
- **Get User by ID (Secure)**: `GET /api/v1/secure/users/{id}` (JWT token required)

#### **Non-Secure Endpoints**
- **Base**: `GET /api/v1/non-secure`
- **Welcome**: `GET /api/v1/non-secure/welcome`
- **Path Variable**: `GET /api/v1/non-secure/items/{id}`
- **Request Parameter**: `GET /api/v1/non-secure/search?name=example`

### JWT Token

For all secure endpoints, a JWT token obtained from the `/login` API must be included in the `Authorization` header as a Bearer token.

### Testing

Run the following command to execute unit tests:

```bash
./mvnw test
```

## License

This project is licensed under the MIT License - see the LICENSE file for details.
