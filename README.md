# Spring Boot Testing Project

![Spring Boot Logo](https://spring.io/images/spring-logo.png)

This repository contains a simple CRUD (Create, Read, Update, Delete) Rest API, and it focuses on testing each layer of the application using a Behavior-Driven Development (BDD) style. The main objective is to demonstrate how to write unit and integration tests for a Spring Boot application.

## Testing Libraries

The following testing libraries were used for creating the tests:

- **Mockito**: A popular Java mocking framework used to create mock objects for testing.

- **JUnit 5**: The latest version of the JUnit framework for writing and running unit tests.

- **AssertJ**: A fluent assertion library that provides expressive and easy-to-read assertions.

- **Hamcrest**: A library for writing matcher objects, used for more flexible and readable assertions.

## Getting Started

To run this project locally or explore the tests, follow these steps:

1. Clone the repository to your local machine:

```
git clone https://github.com/kissmylala/spring-boot-testing.git
```

2. Open the project in your favorite Java IDE.

3. Browse through the different packages to see the tests written for each layer of the application.

## Test Layers

The tests in this project are organized based on the layers of the application:

- **Unit Tests**: These tests focus on testing individual units or components in isolation, such as service classes and utility methods.

- **Integration Tests**: These tests focus on testing the integration between different units or components of the application. They ensure that the different parts of the application work together as expected.

EmployeeRepositoryTests
The class EmployeeRepositoryTests is designed to test the functionality of the EmployeeRepository class, which interacts with the H2 in-memory database. Since this test class is intended for initial setup and configuration of the database, it is expected to throw an exception during its execution.




## How to Run Tests

To run the tests, you can use your IDE's built-in test runner or run the following command in your terminal:

```
mvn test
```

