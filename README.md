# TinyLink - URL Shortener

**Live Demo**: [https://tinylink-45j9.onrender.com](https://tinylink-45j9.onrender.com)

## About Project
TinyLink is a simple, fast, and efficient URL shortening service. It converts long, unwieldy URLs into concise aliases. Built using a robust backend architecture, it features a clean and interactive user interface that allows users to instantly generate and copy shortened links. The application is fully containerized and currently deployed live on Render.

## Features

- **Shorten URLs**: Create short aliases for long URLs
- **Redirect**: Automatic redirection from short URL to original URL
- **In-Memory Storage**: Fast data access with H2 database
- **REST API**: Clean and simple RESTful endpoints

## Tech Stack

- **Backend:** Java 21, Spring Boot 3, Spring Data JPA, Hibernate
- **Database:** H2 In-Memory Database
- **Frontend:** HTML5, Vanilla JavaScript, CSS3
- **Deployment:** Docker, Render (via Blueprint)
- **Build Tool:** Maven

## 🎯 Interview Focus: System Design & LLD

This project is built to demonstrate strong **Low-Level Design (LLD)** concepts and production-ready architectural decisions commonly discussed in backend engineering interviews:

### 1. Architectural Patterns
- **Service-Oriented Architecture:** Clear, strict separation of concerns into Controller (HTTP/routing), Service (business logic & cryptography), and Repository (data access) layers.
- **Stateless Backend:** The application handles HTTP requests statelessly. It scales horizontally right out of the box because no session state is maintained on the application level.

### 2. URL Generation Algorithm (DSA)
- **Base62 Encoding:** Converts an auto-incrementing Base-10 database ID into a Base62 string (`a-z, A-Z, 0-9`), generating incredibly short and collision-free URLs mathematically without expensive heavy hashing functions.
- **Concurrency Handling:** Implements thread-safe atomic counters (`AtomicLong`) to mimic distributed ID generation natively before applying Base62, preventing race conditions during high-volume concurrent traffic.

### 3. SOLID & LLD Principles
- **Single Responsibility Principle (SRP):** The `UrlController` only handles HTTP requests, while the `UrlService` strictly handles algorithmic conversion, encoding, and logic.
- **Dependency Injection (DI):** Spring's core IoC container is heavily utilized for constructor injection, making the codebase decoupled and inherently testable.
- **Robust Error Handling:** Designed with predictable HTTP status codes (200 OK, 400 Bad Request, 404 Not Found, 302 Found redirection). 

### 4. Scalability Discussions (HLD Transition)
To scale this to millions of requests (a common interview extension), the design is laid out for the following iterations:
- **Database:** Migrate from H2 to a NoSQL store (Cassandra, DynamoDB) or a sharded relational database. 
- **Caching:** Introduce a Redis instance or CDN layer to achieve highly accelerated `O(1)` concurrent reads for the redirect endpoint.
- **Distributed ID Generation:** Transition from an `AtomicLong` to a distributed Ticket Server or Twitter Snowflake implementation to track auto-incrementing IDs across multiple instances.

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd url_shortener
   ```

2. Build the project:
   ```bash
   mvn clean install
   ```

### Running the Application

Start the application using Spring Boot:

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`.

## API Endpoints

### Create Short URL

**POST** `/api/shorten`

**Request Body:**
```json
{
  "url": "https://www.example.com/very/long/url/that/needs/to/be/shortened"
}
```

**Response:**
```json
{
  "shortUrl": "http://localhost:8080/api/r/a1b2c3d4",
  "originalUrl": "https://www.example.com/very/long/url/that/needs/to/be/shortened"
}
```

### Redirect to Original URL

**GET** `/api/r/{shortCode}`

**Example:** `GET /api/r/a1b2c3d4`

Redirects to the original URL associated with the short code.

### Get All URLs

**GET** `/api/urls`

Returns a list of all shortened URLs.

## Database

The application uses H2 in-memory database, which means:
- All data is stored in memory
- Data is lost when the application restarts
- Perfect for development and testing

## Project Structure

```
src/main/java/com/example/urlshortener/
├── UrlShortenerApplication.java  # Main application class
├── controller/                   # REST controllers
│   └── UrlController.java
├── model/                        # JPA entities
│   └── UrlRecord.java
├── repository/                   # Spring Data repositories
│   └── UrlRepository.java
└── service/                      # Business logic
    └── UrlService.java
```

## Configuration

Application properties are defined in `src/main/resources/application.properties`:

```properties
server.port=8080

spring.datasource.url=jdbc:h2:mem:urlshortener
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
```

## Testing

Run tests using Maven:

```bash
mvn test
```

Made with ❤️ by Dilip Kumar
