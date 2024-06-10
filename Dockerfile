FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/booking-system-0.0.1-SNAPSHOT.jar booking-system-0.0.1-SNAPSHOT.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "booking-system-0.0.1-SNAPSHOT.jar"]
