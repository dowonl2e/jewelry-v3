FROM openjdk:17-alpine

COPY build/libs/jewelry-3.1.1-SNAPSHOT.jar jewelry.jar

ENTRYPOINT ["java","-Dspring.profiles.active=docker", "-jar", "jewelry.jar"]
