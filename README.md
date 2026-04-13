# TinyLink - URL Shortener

A simple, fast, and efficient URL shortening service built with Spring Boot and H2 in-memory database.

## Features

- **Shorten URLs**: Create short aliases for long URLs
- **Redirect**: Automatic redirection from short URL to original URL
- **In-Memory Storage**: Fast data access with H2 database
- **REST API**: Clean and simple RESTful endpoints

## Tech Stack

- **Java 17**
- **Spring Boot 3**
- **Spring Data JPA**
- **H2 Database**
- **Maven**

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

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
