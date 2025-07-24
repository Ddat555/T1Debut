FROM maven:latest as BUILD
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests



FROM openjdk:21
WORKDIR /app
EXPOSE 8083
COPY --from=BUILD ./app/target/MetricService-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "MetricService-0.0.1-SNAPSHOT.jar"]