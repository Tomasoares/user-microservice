FROM openjdk:8-jdk-alpine

ENV SPRING_DATA_MONGODB_URI mongodb://creativedriver:cr34t1v3dr1v3r@192.168.0.24:27017/user-microservice
EXPOSE 8091

ARG JAR_FILE
COPY ./target/user-microservice.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]