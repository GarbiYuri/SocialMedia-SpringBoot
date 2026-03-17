# Estágio 1: Build (Onde usamos o Maven para compilar)
FROM maven:3.9.12-amazoncorretto-25 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline


COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:25-alpine

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080


ENTRYPOINT ["java", "-jar", "app.jar"]