# ----- 1° ETAPA: BUILD ----- #
FROM maven:3.9.14-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src

RUN mvn clean package -DskipTests

# ----- 2° ETAPA: RUNTIME ----- #
FROM alpine/java:21-jdk

ENV API_PORT_DEFAULT=${API_PORT_DEFAULT}

WORKDIR /app

COPY --from=build /app/target/app.jar app.jar

EXPOSE ${API_PORT_DEFAULT}

ENTRYPOINT ["java", "-jar", "app.jar"]