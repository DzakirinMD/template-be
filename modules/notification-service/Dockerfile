FROM eclipse-temurin:17-jdk-focal
LABEL maintainer="DzakirinMD"

ARG JAR_FILE=target/notification-service-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar

EXPOSE 10004

CMD ["java", "-jar", "app.jar"]
