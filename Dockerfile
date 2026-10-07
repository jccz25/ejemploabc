# Etapa 1: Compilacion (Build)
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Etapa 2: Imagen final de ejecucion (Runtime)
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Puerto donde corre Spring Boot
EXPOSE 8080

# Comando para iniciar la aplicacion
ENTRYPOINT ["java", "-jar", "app.jar"]

