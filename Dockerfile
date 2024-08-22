# Use an official Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build

# Set the working directory inside the container
WORKDIR /app

# Copy the pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy the source code into the container
COPY src ./src

# Package the application
RUN mvn clean package

# Use an OpenJDK runtime image to run the app
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/target/*.jar /app/myclinic.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "/app/myclinic.jar"]