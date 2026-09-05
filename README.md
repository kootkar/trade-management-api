# Trade Management API

A REST API for managing stock trades — create, read, update, delete, filter, sort, and paginate trade records. Built with Spring Boot, Spring Data JPA, and PostgreSQL, following a layered architecture (Controller → Service → Repository) with DTOs, input validation, centralized error handling, and API key authentication.

This project was built as a hands-on way to learn Spring Boot and backend development fundamentals.

## Features

- Full CRUD for trades (create, read, update, delete)
- Filtering by symbol, status, and side
- Sorting and pagination on list endpoints
- Request validation with clear, field-level error messages
- Centralized exception handling (clean JSON error responses instead of stack traces)
- API key authentication on all endpoints
- Unit tests for service-layer business logic (JUnit 5 + Mockito)

## Tech Stack

- **Java 17**
- **Spring Boot** (Web, Data JPA, Validation)
- **PostgreSQL** — persistent relational database
- **Maven** — build and dependency management
- **Lombok** — reduces boilerplate (getters/setters/constructors)
- **JUnit 5 + Mockito** — unit testing

## Project Structure

```
src/main/java/com/trade/trade/management/
├── controller/     # REST endpoints (HTTP layer)
├── service/        # Business logic
├── repository/     # Spring Data JPA repositories
├── model/          # JPA entities (Trade, TradeSide, TradeStatus)
├── dto/            # Request/response objects, decoupled from entities
├── exception/      # Custom exceptions + global exception handler
└── security/       # API key filter
```

## Getting Started

### Prerequisites

- Java 17+
- Maven
- PostgreSQL running locally

### Setup

1. Create a PostgreSQL database:
   ```sql
   CREATE DATABASE tradedb;
   ```

2. Configure `src/main/resources/application.properties` with your database credentials and API key:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/tradedb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   app.api-key=your-secret-key
   ```

3. Run the app:
   ```bash
   ./mvnw spring-boot:run
   ```

The API will be available at `http://localhost:8080`.

### Running Tests

```bash
./mvnw test
```

## API Endpoints

All endpoints require an `X-API-KEY` header.

| Method | Endpoint             | Description                          |
|--------|----------------------|---------------------------------------|
| POST   | `/trades`             | Create a new trade                    |
| GET    | `/trades`             | List trades (supports filters below)  |
| GET    | `/trades/{id}`        | Get a single trade by ID              |
| PUT    | `/trades/{id}`        | Update a trade                        |
| DELETE | `/trades/{id}`        | Delete a trade                        |

**Query parameters for `GET /trades`:**
- `symbol` — filter by ticker symbol (e.g. `AAPL`)
- `status` — filter by status (`OPEN` or `CLOSED`)
- `side` — filter by side (`BUY` or `SELL`)
- `page`, `size` — pagination (defaults: `page=0`, `size=10`)
- `sortBy` — field to sort by (default: `id`)

### Example Request

```bash
curl -X POST http://localhost:8080/trades \
  -H "Content-Type: application/json" \
  -H "X-API-KEY: your-secret-key" \
  -d '{
    "symbol": "AAPL",
    "quantity": 10,
    "price": 150.50,
    "side": "BUY",
    "status": "OPEN",
    "timestamp": "2026-08-31T10:00:00"
  }'
```

### Example Response

```json
{
  "id": 1,
  "symbol": "AAPL",
  "quantity": 10,
  "price": 150.50,
  "side": "BUY",
  "status": "OPEN",
  "timestamp": "2026-08-31T10:00:00"
}
```

## Design Notes

- **DTOs vs. entities**: incoming requests are validated and mapped through a `TradeRequest` DTO rather than exposing the `Trade` entity directly — this keeps the API contract decoupled from the database schema.
- **Global exception handling**: a `@RestControllerAdvice` catches validation failures and "not found" errors, returning consistent, structured JSON responses (400/404) instead of raw stack traces.
- **API key auth**: a lightweight `OncePerRequestFilter` checks for a valid `X-API-KEY` header on every request. A production system with multiple users would use a more robust approach (e.g. JWT-based auth), which this project intentionally kept out of scope.

## Possible Next Steps

- JWT-based authentication for multi-user support
- Integration tests against a real (test) database
- Dockerize the app and database for easier setup
- OpenAPI/Swagger documentation
