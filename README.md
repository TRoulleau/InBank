# Internship project for Inbank - Loan Decision Engine

This project is a full-stack application designed to automate loan decisions based on a person's credit modifier and requested loan attributes. 

## Features

- **Decision Logic**: Calculates the maximum loan amount based on credit modifiers.
- **Period Adjustment**: If the requested amount is not available for the selected period, it automatically searches for an alternative period (12-60 months).
- **Responsive UI**: A modern frontend to interact with the decision engine.

---

## Tech Stack

**Backend:**
- **Java 21** / **Spring Boot**
- **Gradle** as the build tool
- **JUnit** for unit testing

**Frontend:**
- **Angular 21** for building the user interface
- **TypeScript** for type safety
- **CSS3** for styling
- **Vitest** for unit testing

---

## Choices and Justifications

For this project, I chose Java 21 and Spring Boot for the backend due to their robustness, scalability, and extensive ecosystem. I actually had never worked with Spring Boot before, but I had planned to learn it and am familiar with Java, so it was a great opportunity to gain hands-on experience with a new framework while using my existing Java knowledge.
I previously had experience with Angular, so I chose Angular 21 for the frontend to use my existing skills and ensure a smooth development process. 
During my academic studies, we has a course on web development where we used Angular alongside TypeORM, so I was already familiar with Angular's component-based architecture and TypeScript.

Java 21 is an LTS version, which ensures long-term support and stability for the backend. Angular 21 is the latest version.
While I am familiar with some CSS frameworks (like Bootstrap), I opted for plain CSS because extensive styling was not a requirement for this project, and I wanted to keep the frontend simple and focused on functionality.

---

**How to run the project:**

0. **Prerequisites**:
   - Ensure you have Java 21, Node.js (with npm), and Angular CLI installed on your machine.
   - For the backend, you will need Gradle installed or you can use the Gradle wrapper included in the project.

1. **Backend**:
   - Navigate to the `back` directory.
   - Run `./gradlew bootRun` to start the Spring Boot application.
   - The server starts on `http://localhost:8080`.
2. **Frontend**:
   - Navigate to the `front` directory.
   - Run `npm install` to install dependencies.
   - Run `ng serve` to start the Angular application.
   - The frontend will be available at `http://localhost:4200`.

---

## Project Structure

```text
.
├── back/           # Spring Boot Application
├── front/          # Frontend Application
└── README.md          # Project documentation
```