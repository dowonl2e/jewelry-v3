FROM openjdk:17-alpine

ARG JAR_FILE=build/libs/jewelry-3.1.1-SNAPSHOT.jar

COPY ${JAR_FILE} jewelry.jar

ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar", "jewelry.jar"]