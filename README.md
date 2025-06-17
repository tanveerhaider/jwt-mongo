# JWT Mongo Spring Boot Application

This is a Spring Boot application that demonstrates the use of JSON Web Tokens (JWT) for authentication and MongoDB as the database. The application is designed to provide secure authentication and authorization for users.

## Features

- **JWT Authentication**: Securely authenticate users using JSON Web Tokens.
- **MongoDB Integration**: Store and retrieve user data using MongoDB.
- **Spring Security**: Protect endpoints and manage user roles.
- **RESTful API**: Expose endpoints for user authentication and data access.
- **Validation**: Validate user input using Spring Boot's validation framework.

## Prerequisites

Before running the application, ensure you have the following installed:

- Java 17 or higher
- Maven 3.6 or higher
- MongoDB (running locally or accessible remotely)

## Getting Started

### Clone the Repository


git clone https://github.com/your-username/jwt-mongo.git
cd jwt-mongo

Configuration
MongoDB Configuration: Update the application.properties file in the src/main/resources directory with your MongoDB connection details:  
spring.data.mongodb.uri=mongodb://localhost:27017/your-database-name

JWT Configuration: Add the following properties to the application.properties file:  
jwtSecret=your-secret-key
jwtExpirationMs=3600000

Build and Run the Application
Build the project using Maven:  
mvn clean install
Run the application:  
mvn spring-boot:run

The application will start on http://localhost:8080.  
API Endpoints
Authentication
POST /api/auth/signin: Authenticate a user and return a JWT token.
POST /api/auth/signup: Register a new user.
Protected Endpoints
GET /api/test/user: Accessible to users with the ROLE_USER.
GET /api/test/admin: Accessible to users with the ROLE_ADMIN.
Project Structure
src/main/java/com/simplejava: Contains the main application code.
controller: REST controllers for handling API requests.
security: Security configuration and JWT-related services.
service: Business logic and user-related services.
model: Entity classes for MongoDB.
repository: MongoDB repositories.
src/main/resources: Contains configuration files like application.properties.

Test the Application
Use /api/auth/signin to authenticate and get a JWT token.
Use the token in the Authorization header (e.g., Bearer <token>) to access protected endpoints.