FROM eclipse-temurin:21-jdk-alpine

LABEL authors="Cherif Diallo"

WORKDIR /l3gl

COPY target/l3gl-money-0.0.1-SNAPSHOT.jar l3gl-money.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/l3gl/l3gl-money.jar"]
