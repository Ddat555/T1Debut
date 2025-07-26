FROM maven:latest as BUILD
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests



FROM openjdk:21
WORKDIR /app
EXPOSE 8080
COPY --from=BUILD ./app/target/authService-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "authService-0.0.1-SNAPSHOT.jar"]