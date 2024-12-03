FROM openjdk:21
WORKDIR /app
COPY build/libs/user_service-0.0.1-SNAPSHOT.jar /app/app.jar

CMD ["java", "-jar", "app.jar"]
EXPOSE 8080