FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/animestyle-backend-0.0.1-SNAPSHOT.jar anime-style-app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "anime-style-app.jar"]
