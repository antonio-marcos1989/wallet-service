FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY target/wallet-service.jar wallet-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "wallet-service.jar"]