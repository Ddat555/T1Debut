FROM maven:latest as BUILD
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:21
WORKDIR /app
COPY --from=BUILD ./app/target/service2-0.0.1-SNAPSHOT.jar .
CMD ["java", "-jar", "service2-0.0.1-SNAPSHOT.jar"]