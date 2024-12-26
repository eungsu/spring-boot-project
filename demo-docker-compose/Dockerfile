# Base image
FROM openjdk:17-jdk-alpine

# Application directory
WORKDIR /app

# Copy the Spring Boot JAR file
COPY target/demo-docker-compose-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
