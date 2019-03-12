FROM openjdk:8-jdk-alpine
ARG JAR_FILE
COPY target/user-microservice.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]