FROM openjdk:17-jdk-alpine

ARG JAR_FILE=target/*.jar

# after installing maven lifecycle
COPY ./target/bidding-backend-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app.jar"]
