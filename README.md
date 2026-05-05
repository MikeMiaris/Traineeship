# Traineeship Management Application

## Overview

This project implements a web-based **Traineeship Management Application**, developed as part of a Software Engineering class assignment.

The system supports the management of traineeship positions, including:

* Creation of positions by companies
* Applications by students
* Assignment of positions and supervisors
* Evaluation of traineeships

The application involves four main user roles:

* Students
* Companies
* Professors
* Traineeship Committee Members


## Development Approach

The development of the project was guided by the assignment’s structure:

* Functionality was derived from a set of **user stories**
* Work was organized incrementally (similar to **Sprint-based** development)
* Each feature was implemented and then validated with tests where applicable


## Architecture

The application follows a version of the MVC pattern:

* **Controllers** to handle incoming requests and user interaction

* **Services** to contain the core application logic

* **Repositories** to handle data access using Spring Data JPA

* **Domain Model** which represents the main entities of the system


## Domain Model

The system is based on the following core entities:

* **User** (with roles)
* **Student**
* **Company**
* **Professor** (that can serve as traineeship committee members)
* **TraineeshipPosition**
* **Evaluation**

### Relationships

* A company can offer multiple traineeship positions
* A student is assigned to one traineeship
* A professor can supervise multiple traineeships
* Evaluations are provided by both professors and companies


## Matching Logic

The system supports different ways of matching students to traineeship positions:

* Based on **interests**
* Based on **preferred location**
* A combination of both

Interest matching is implemented using **Jaccard similarity logic**.


## Use Cases

### User (all of the below)

* User registration
* User login
* User logout

### Students

* Manage profile
* Apply for traineeships
* Fill in a logbook

### Companies

* View advertised traineeship positions
* View assigned traineeship positions
* Announce positions
* Delete positions
* Evaluate trainees

### Professors

* View supervised traineeships
* Evaluate trainees

### Committee Members

* View traineeship positions
* View student profiles
* Assign students and supervisors to traineeship positions
* Monitor progress and finalize results

## Testing

Testing was implemented to verify parts of the application:

* Unit tests for selected components
* Use of testing tools such as JUnit and Mockito

## Technologies Used

* Java
* Spring Boot
* Spring MVC
* Spring Data JPA
* MySQL
* JUnit

## How to Run

1. Clone the repository
2. Have a local MySQL server up and running
3. Run the application using Eclipse as an IDE
4. Open in browser:

   ```
   http://localhost:8080
   ```


## Notes

This project focuses on applying fundamental software engineering concepts such as:

* Layered architecture
* Separation of concerns
* Basic testing practices

The goal was to build a functional and reasonably structured system rather than a production-ready application.
