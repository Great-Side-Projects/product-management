FROM openjdk:22-jdk-slim

WORKDIR /app
COPY target/*.jar /app/discovery-server.jar
EXPOSE 8761

ENTRYPOINT ["java", "-jar", "discovery-server.jar"]