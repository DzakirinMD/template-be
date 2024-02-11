FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/template-be-0.0.1.jar /app/

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "template-be-0.0.1.jar"]
