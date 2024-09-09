FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY ./target/Inventory-service.jar /app/Inventory-service.jar

EXPOSE 8081
ENTRYPOINT ["java", "-jar", "app/Inventory-service.jar"]