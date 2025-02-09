FROM openjdk:22-jdk-slim

WORKDIR /app
COPY target/*.jar /app/api-gateway.jar
EXPOSE 8084

ENTRYPOINT ["java", "-jar", "api-gateway.jar"]