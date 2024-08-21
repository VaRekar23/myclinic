# Use an official Maven image to build the app
FROM maven:3.8.5-openjdk-17 AS build

# Install JDK 21
RUN apt-get update && \
    apt-get install -y wget && \
    wget https://download.java.net/java/early_access/jdk21/22/GPL/openjdk-21-ea+22_linux-x64_bin.tar.gz && \
    tar -xzf openjdk-21-ea+22_linux-x64_bin.tar.gz -C /opt/ && \
    update-alternatives --install /usr/bin/java java /opt/jdk-21/bin/java 1 && \
    update-alternatives --install /usr/bin/javac javac /opt/jdk-21/bin/javac 1 && \
    update-alternatives --install /usr/bin/javadoc javadoc /opt/jdk-21/bin/javadoc 1 && \
    update-alternatives --set java /opt/jdk-21/bin/java && \
    update-alternatives --set javac /opt/jdk-21/bin/javac && \
    update-alternatives --set javadoc /opt/jdk-21/bin/javadoc

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
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build stage to the runtime stage
COPY --from=build /app/target/myclinic.jar /app/myclinic.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run the app
ENTRYPOINT ["java", "-jar", "/app/yourappname.jar"]
