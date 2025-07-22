# ---------- Stage 1: Build ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder

WORKDIR /app

# Copy the entire blog source code
COPY . .

# Build the JAR using Maven
RUN mvn clean package -DskipTests

# ---------- Stage 2: Run ----------
FROM openjdk:21-jdk-slim

WORKDIR /app

# Copy the built JAR from Stage 1
COPY --from=builder /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]