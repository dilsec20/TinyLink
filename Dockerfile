# Build stage
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# Copy maven wrapper and project metadata first for better caching
COPY mvnw pom.xml ./
COPY .mvn .mvn

# Copy source code and package
COPY src src

RUN chmod +x mvnw && ./mvnw -DskipTests clean package -DskipDocs -q

# Production stage
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# For Render: honor $PORT with fallback 8080
ENTRYPOINT ["sh", "-c", "java -jar /app/app.jar --server.port=${PORT:-8080}"]
