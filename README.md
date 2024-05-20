# Library Management System

## Overview
This document provides instructions on running and interacting with the API endpoint for the Library Management System.

## Running the Application

### Prerequisites
- Java Runtime Environment (JRE) 11 or later

### Build Instructions
1. Clone the project repository from your version control system (e.g., Git).
2. Navigate to the project directory in your terminal.
3. Use a build tool like Maven or Gradle to build the project. Refer to the build tool's documentation for specific commands.

### Configuration
The application expects a database connection to be established. You can achieve this through a configuration file, which is the application properties. The specific configuration details will depend on your chosen database technology, but the one used in the application is MySQL and Aiven service plan.

#### Using Configuration File
Set the following details:
```
spring.application.name: The name of the project
spring.jpa.hibernate.ddl-auto: set to update
spring.datasource.url: set to jdbc:mysql://HOST:PORT/dbname
spring.datasource.username: The username for the Aiven resource
spring.datasource.password: The password for accessing the database
spring.datasource.driver-class-name: com.mysql.cj.jdbc.Driver
```

### Aiven Resource Usage
This application leverages an Aiven resource for the database. Refer to the Aiven documentation for your chosen database service plan and technology for detailed configuration steps: [Link to Aiven Documentation]

### Startup Instructions
1. Ensure the database connection is configured correctly (environment variables or configuration file).
2. Start the application using the appropriate command: `mvn spring-boot:run` (in case Maven was used for running the application).

## API Endpoints
The following table lists the API endpoints available in the Library Management System:

| HTTP Method | URL Path | Description | Expected Request Body (if applicable) | Expected Response Format |
| --- | --- | --- | --- | --- |
| GET | /patrons/view | Retrieves a list of all patrons. | None | List of Patron objects |
| GET | /patrons/{id} | Retrieves a specific patron by ID. | None | Patron object |
| POST | /patrons/create | Creates a new patron. | Patron object with details like name, contact information, etc. | Patron object with the generated ID |
| PUT | /patrons/{id} | Updates an existing patron. | Patron object with updated details | Patron object reflecting the changes |
| DELETE | /patrons/{id} | Deletes a patron by ID. | None | Success message or empty response |
| GET | /books/view | Retrieves a list of all books. | None | List of Book objects |
| GET | /books/{id} | Retrieves a specific book by ID. | None | Book object |
| POST | /books/create | Creates a new book. | Book object with details like title, author, etc. | Book object with the generated ID |
| PUT | /books/{id} | Updates an existing book. | Book object with updated details | Book object reflecting the changes |
| DELETE | /books/{id} | Deletes a book by ID. | None | Success message or empty response |
| POST | /borrow/{bookId}/patron/{patronId} | Borrows a book by a patron. | JSON object with key "expectedReturnDate" containing the expected return date. | Success message ("Book borrowed successfully!") or error messages (e.g., book not found, unavailable) |
| PUT | /return/{bookId}/patron/{patronId} | Returns a borrowed book. | None | Success message ("Book returned successfully!") or error message (e.g., book not found) |

**Additional Notes**:
- This documentation provides a general overview. Specific implementation details might vary.
- Refer to the source code for a more detailed understanding of the application's logic.
