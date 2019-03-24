FROM openjdk:8-jdk-alpine
COPY target/image-scaler-0.1.0.jar /app/image-scaler.jar
ENTRYPOINT ["java","-cp","/app/image-scaler.jar"]