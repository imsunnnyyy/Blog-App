# official OpenJDK 21 image
FROM openjdk:21-jdk-slim

# Set working directory inside container
WORKDIR /app

# Copy the JAR file from target folder to container
COPY target/*.jar app.jar

# Expose Spring Boot default port
EXPOSE 8080

# Run the jar file when container starts
ENTRYPOINT ["java", "-jar", "app.jar"]