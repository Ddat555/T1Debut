FROM maven:latest as BUILD
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn install -DskipTests

