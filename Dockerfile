FROM maven:3.8-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml /app
COPY src /app/src
RUN mvn clean package -DskipTests
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=build /app/target/library.jar /app/library.jar
COPY --from=build /app/src/main/resources/db /app/db
CMD ["java", "-jar", "library.jar"]