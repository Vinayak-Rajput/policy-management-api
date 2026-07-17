# Project Architecture
## Layered Architecture (Separation of Concerns)
The application follows 3-tier Spring Boot architecture:
* **Controllers:** Responsible only for handling HTTP traffic, reading bodies, and returning HTTP responses.
* **Services:** Contains the business logic and rules.
* **Repositories:** Handles data storage and retrieval. 

# Implementation Approach
## 1. In-Memory Storage Strategy
To meet the requirement of not using a formal database, the application utilizes an in-memory storage pattern.

**Thread Safety:** Use of Collection Framework tools like Concurrent Hash Maps and Synchronized Blocks to make certain variables thread safe.

**Data Structures:** Java Collections used in the repositories to mimic table operations (find by ID, save, find all).

## 2. Data Transfer Object (DTO) Pattern
The API separates internal database models from external JSON payloads using DTOs which enables checking of Data being transferred at Endpoint boundaries.

**Validation:** Allows the use of Jakarta `@Valid` annotations (`@NotNull`, `@Min`) at the API boundary, rejecting bad payloads before they ever reach the service layer.

## 3. Exception Handling
Instead of using `try-catch` blocks repeatedly, `@RestControllerAdvice` is implemented through a separate java file.
* It intercepts `MethodArgumentNotValidException` (bad DTOs) and `IllegalArgumentException` (business rule violations).
* It translates ugly stack traces 500 errors, with 400 Bad Request statuses.

## 4. Proposals Service Handling
Divided Process into 2 Phases:

**Draft (`POST /proposals`):** Accepts initial data and validates bounds, saving the record as `DRAFT` without a policy number.

**Submit (`POST /proposals/{id}/submit`):** Evaluates the draft, records policy proposal as `SUBMITTED`, generates policy, and saves details into `AuditService`.

# Design Decisions
**Two-Step State Machine:** The proposal flow was modeled as a strict state machine to reflect real-world insurance underwriting.

*Step 1 (Create):* Accepts data, validates bounds, and saves as `DRAFT`.

*Step 2 (Submit):* Evaluates the draft by ID, transitions the state to `SUBMITTED`, generates the unique `POL-XXXXXXXX` policy number, and triggers an event to the `AuditService`.

**UUID Generation:** Policy numbers are generated using `UUID.randomUUID()` to ensure high entropy and uniqueness without relying on database auto-increment sequences.

# Trade-Offs Considered
**In-Memory Storage vs. Persistent Database:** To meet the project requirement of not using a formal external database, standard Java Collections (Lists/Maps) were used in the repository layer. 

*Trade-off:* Data is highly volatile and lost upon server restart. 

*Reasoning:* This prioritizes lightweight, zero-configuration local execution and testing over long-term persistence, which is ideal for a reviewer evaluating the codebase.

# Assumptions Made
**Single-Node Deployment:** It is assumed the application will run on a single JVM instance. If deployed in a distributed/multi-node environment, the current in-memory storage would cause data inconsistencies between nodes.

**Security & Authentication:** It is assumed that API gateway authentication (e.g., OAuth2, JWT) is handled upstream, as no strict Spring Security filters were required for this specific assignment scope.

## 6. Reasoning Behind the Solution
The primary goal of this solution was to build software fulfilling the base rewuirements. By implementing DTOs, exception handlers for business logic, and a layered architecture, the codebase is desgined to not allow bad data and it is easy to write automated tests for testing various endpoints plus also manual testing using Postman.