FROM gradle:latest AS build
COPY ./ /home/gradle/src
WORKDIR /home/gradle/src

RUN gradle build —no-daemon

FROM openjdk:17-jdk-alpine
COPY —from=build /home/gradle/src/build/libs/*.jar /app/app.jar

EXPOSE 8080
CMD ["java", "-jar", "/app/app.jar"]