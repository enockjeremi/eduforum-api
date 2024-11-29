<p align="center">
  <img src="https://raw.githubusercontent.com/enockjeremi/eduforum-api/refs/heads/main/src/main/resources/static/badge.png" alt="Eduforum Logo" width="300">
</p>
# Eduforum

Rest API created for the Alura Challenge.

API for the Eduforum platform, developed in Java using Spring Boot.

## Installation

Java JDK 17 is required for this project.  
In the root directory, you'll find a `.env.example` file. Add the necessary environment variables for database connection there.

To run the database, use a Docker container. You'll also find a `.yml` file with a pre-configured setup for the container.

## Resources Used

This project leverages various dependencies, each tailored to specific tasks:

- **Spring Data JPA**: For database queries and management.
- **Spring Security**: For authentication and authorization control.
- **Auth0**: To generate access tokens (JWT).
- **Bean Validation**: To validate DTOs.
- **Flyway**: To manage database migrations.
- **Lombok**: To reduce boilerplate code.
- **Spring Doc (OpenAPI)**: For API documentation.

## Features

- **CRUD for Courses**
- **CRUD for Topics**
- **CRUD for Answers**
- **User Authentication**
- **Role-Based Authorization**
- **Search Topics by Title**
- **List Topics by Course**
- **List Topics by Course Category**
- **Author Attribution**: Every topic and answer must have an author.
- **Editing and Deletion Restrictions**:
    - Only the author of a topic or answer can edit or delete it.
    - Admin users have permissions to edit and delete any topic or answer.
- **Solution Selection**:
    - When a user selects an answer as the solution to their topic, the topic's status is set to inactive, indicating no further responses are needed.
    - Both the topic's author and an admin can select the solution answer.

## Notes

While the challenge didn’t require adding so many features, it was a great learning experience.  
I really enjoyed working with Spring Boot—it’s a very powerful framework.

The most challenging part was implementing authorization, as it wasn’t covered in the courses I took. However, with the help of Google and the documentation, everything is possible.

## Author

- [@enockjeremi](https://www.github.com/enockjeremi)
