# Student Management System (SMS)

A RESTful Spring Boot application for managing student records with MySQL database integration.

## Overview

The Student Management System provides a complete CRUD API for managing student information including personal details, contact information, and addresses. The application features validation, error handling, and comprehensive documentation.

## Features

- **Student CRUD Operations**: Create, Read, Update, Delete student records
- **Data Validation**: Input validation with custom error messages
- **Database Integration**: MySQL database with JPA/Hibernate
- **RESTful API**: Well-documented REST endpoints
- **Error Handling**: Comprehensive error handling and validation responses
- **Address Management**: Optional address field for student records

## Technology Stack

- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **MySQL Database**
- **Hibernate ORM**
- **Maven**

## Project Structure

```
src/main/java/com/company/sms/
├── controller/
│   └── StudentController.java    # REST API endpoints
├── model/
│   └── Student.java              # Student entity
├── repository/
│   └── StudentRepository.java    # JPA repository
├── service/
│   └── StudentService.java       # Business logic
└── SmsApplication.java           # Main application class
```

## Database Configuration

### Prerequisites

1. **MySQL Server** installed and running
2. **Database** created with the following command:
   ```sql
   CREATE DATABASE sms;
   ```

### Configuration

The application connects to MySQL using the following configuration (in `application.properties`):

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/sms?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=12345678
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

**Note**: Update the username and password to match your MySQL configuration.

## API Endpoints

### Base URL
```
http://localhost:8080
```

### Student Management Endpoints

| Method | Endpoint | Description | Request Body | Response |
|--------|----------|-------------|--------------|----------|
| GET | `/api/v1/students` | Get all students | - | List of students |
| GET | `/api/v1/students/{id}` | Get student by ID | - | Student object or 404 |
| POST | `/api/v1/students` | Create new student | Student JSON | Created student (201) |
| PUT | `/api/v1/students/{id}` | Update student | Student JSON | Updated student or 404 |
| DELETE | `/api/v1/students/{id}` | Delete student | - | 204 No Content or 404 |

## Student Object Structure

### JSON Format
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "rollNo": "2023-001",
  "studentClass": "10A",
  "address": "123 Main Street, City, State 12345"
}
```

### Field Validation
- **firstName**: Required, 2-50 characters
- **lastName**: Required, 2-50 characters
- **email**: Required, valid email format, unique
- **rollNo**: Required, unique identifier for each student
- **studentClass**: Required field for class/grade assignment
- **address**: Optional, maximum 500 characters

## Usage Examples

### Create a Student
```bash
curl -X POST http://localhost:8080/api/v1/students \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Smith",
    "email": "alice.smith@example.com",
    "rollNo": "2023-001",
    "studentClass": "10A",
    "address": "456 Oak Avenue, Springfield, IL 62701"
  }'
```

### Get All Students
```bash
curl http://localhost:8080/api/v1/students
```

### Get Student by ID
```bash
curl http://localhost:8080/api/v1/students/1
```

### Update Student
```bash
curl -X PUT http://localhost:8080/api/v1/students/1 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "Alice",
    "lastName": "Johnson",
    "email": "alice.johnson@example.com",
    "rollNo": "2023-002",
    "studentClass": "10B",
    "address": "789 Pine Street, Springfield, IL 62702"
  }'
```

### Delete Student
```bash
curl -X DELETE http://localhost:8080/api/v1/students/1
```

## Error Handling

The API provides comprehensive error handling:

### Validation Errors (400 Bad Request)
```json
[
  {
    "codes": ["Size.student.firstName", "Size.firstName", "Size.java.lang.String", "Size"],
    "arguments": [{"codes": ["student.firstName","firstName"],"defaultMessage":"firstName"},50,2],
    "defaultMessage": "First name must be between 2 and 50 characters",
    "objectName": "student",
    "field": "firstName",
    "rejectedValue": "A",
    "bindingFailure": false,
    "code": "Size"
  }
]
```

### Not Found Errors (404 Not Found)
Returns empty response body with 404 status code.

## Installation and Running

### Prerequisites
- Java 17 or higher
- Maven 3.6 or higher
- MySQL Server 8.0 or higher

### Steps

1. **Clone the repository** (if applicable)
2. **Create the database**:
   ```sql
   CREATE DATABASE sms;
   ```
3. **Configure database credentials** in `src/main/resources/application.properties`
4. **Build the project**:
   ```bash
   ./mvnw clean install
   ```
5. **Run the application**:
   ```bash
   ./mvnw spring-boot:run
   ```
6. **Access the application** at `http://localhost:8080`

## Development

### Project Dependencies

Key dependencies in `pom.xml`:
- `spring-boot-starter-data-jpa` - JPA/Hibernate support
- `spring-boot-starter-web` - Web MVC framework
- `spring-boot-starter-validation` - Bean validation
- `mysql-connector-j` - MySQL JDBC driver

### Database Schema

The application automatically creates the `students` table with the following structure:

```sql
CREATE TABLE students (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    roll_no VARCHAR(255) NOT NULL UNIQUE,
    student_class VARCHAR(255) NOT NULL,
    address VARCHAR(500)
);
```

## Testing

### Running Tests
```bash
./mvnw test
```

### Test Coverage
- Unit tests for service layer
- Integration tests for repository layer
- Controller tests for API endpoints

## License

Copyright (c) 2026 Company. All rights reserved.

This software is the confidential and proprietary information of Company. You shall not disclose such confidential information and shall use it only in accordance with the terms of the license agreement you entered into with Company.

## Contributing

Please follow the established coding standards and ensure all tests pass before submitting changes.

## Support

For technical support or questions, please contact the development team.
