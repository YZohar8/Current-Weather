# Stage 1: Build with Maven
FROM maven:3.9.3-eclipse-temurin-17 AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn clean package

# Stage 2: Run
FROM eclipse-temurin:17-jre

WORKDIR /app

COPY --from=build /app/target/current-weather-1.0-SNAPSHOT.jar ./app.jar

CMD ["java", "-jar", "app.jar"]
