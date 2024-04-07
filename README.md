# ChatApp Service

This is a chat application backend service built with Java, Spring Boot, and SQL. It provides APIs for managing chat rooms and messages.

## Technologies Used

- **Java**: The main programming language used for developing the service.
- **Spring Boot**: A framework used for creating stand-alone, production-grade Spring-based applications.
- **PostgreSQL**: Used for data storage and retrieval.
- **Maven**: A build automation tool used primarily for Java projects.

## Getting Started

To get a local copy up and running, follow these steps:

### Prerequisites

- Java Development Kit (JDK) 17 or later
- Maven 3.6.0 or later
- Docker and Docker Compose
- Postman or any other API testing tool

### Installation

1. Navigate to the project directory and build the project with Maven

   ```bash
   mvn clean install
   ```

2. Open command line(PowerShell) and run the following command to start the PostgreSQL and MongoDB database

   ```bash
   docker-compose up -d
   ```
3. Run the application

   ```bash
   mvn spring-boot:run
   ```
4. Use Postman collection on the project directory for testing the APIs
5. To connect to WS, use the following URL in Postman

   ```bash
   ws://localhost:8080/ws
   ```
   Use Basic Authorization to authenticate the user. Use the following credentials in headers:
     - Username: `john1`
     - Authorization Basic am9objE6dGVzdDE=
     - 
     - Username: `user2`
     - Authorization: Basic dXNlcjE6dGVzdDE=
     -
     - Username: `user3`
     - Authorization: Basic dXNlcjI6dGVzdDE=
- 
   Message format:
   ```json
   {
    "message": "Hello there"
   }
   ```
6. To stop the database, run the following command

   ```bash
   docker-compose down
   ```
   
