FROM maven:3.8.3-openjdk-17 as builder
WORKDIR /app
COPY pom.xml .
COPY src src

RUN mvn clean package -DskipTests

FROM openjdk:17-jdk
VOLUME /tmp
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080