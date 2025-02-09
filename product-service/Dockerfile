FROM openjdk:22-jdk-slim

ARG MONGODB_URI
WORKDIR /app
COPY target/*.jar /app/product-service.jar
EXPOSE 8085
ENV SPRING_DATA_MONGODB_URI=${MONGODB_URI}

ENTRYPOINT ["java", "-jar", "product-service.jar"]