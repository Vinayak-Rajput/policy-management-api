# Architectural & Design Decisions
## 1. Layered Architecture (Separation of Concerns)
The application follows 3-tier Spring Boot architecture:
* **Controllers:** Responsible only for handling HTTP traffic, reading bodies, and returning HTTP responses.
* **Services:** Contains the business logic and rules.
* **Repositories:** Handles data storage and retrieval. 

## 2. In-Memory Storage Strategy
To meet the requirement of not using a formal database, the application utilizes an in-memory storage pattern.
**Thread Safety:** Use of Collection Framework tools like Concurrent Hash Maps and Synchronized Blocks to make certain variables thread safe.
**Data Structures:** Java Collections used in the repositories to mimic table operations (find by ID, save, find all).

## 3. Data Transfer Object (DTO) Pattern
The API separates internal database models from external JSON payloads using DTOs which enables checking of Data being transferred at Endpoint boundaries.

**Validation:** Allows the use of Jakarta `@Valid` annotations (`@NotNull`, `@Min`) at the API boundary, rejecting bad payloads before they ever reach the service layer.

## 4. Exception Handling
Instead of using `try-catch` blocks repeatedly, `@RestControllerAdvice` is implemented through a separate java file.
* It intercepts `MethodArgumentNotValidException` (bad DTOs) and `IllegalArgumentException` (business rule violations).
* It translates ugly stack traces 500 errors, with 400 Bad Request statuses.

## 5. Proposals Service Handling
Divided Process into 2 Phases:
**Draft (`POST /proposals`):** Accepts initial data and validates bounds, saving the record as `DRAFT` without a policy number.
**Submit (`POST /proposals/{id}/submit`):** Evaluates the draft, records policy proposal as `SUBMITTED`, generates policy, and saves details into `AuditService`.