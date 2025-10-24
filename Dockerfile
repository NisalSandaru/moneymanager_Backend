#FROM eclipse-temurin:21-jre
#WORKDIR /app
#COPY target/moneymanager-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar
#RUN mvn clean package -DskipTests
#EXPOSE 9090
#ENTRYPOINT ["java", "-jar", "moneymanager-v1.0.jar"]

# Stage 1: Build the app
FROM maven:3.9.6-eclipse-temurin-21 AS build

# Set working directory inside container
WORKDIR /app

# Copy pom.xml and download dependencies first (for build caching)
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code
COPY src ./src

# Build the project and skip tests
RUN mvn clean package -DskipTests

# Stage 2: Run the app
FROM eclipse-temurin:17-jre

WORKDIR /app

# Copy the built jar from the build stage
COPY --from=build /app/target/moneymanager-0.0.1-SNAPSHOT.jar moneymanager-v1.0.jar

# Expose port (Spring Boot default is 8080)
EXPOSE 8080

# Run the application
ENTRYPOINT ["java", "-jar", "moneymanager-v1.0.jar"]
