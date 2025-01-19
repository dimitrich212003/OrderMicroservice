FROM openjdk:17-jdk-slim
WORKDIR /app
COPY target/domain-0.0.1-SNAPSHOT.jar domain.jar
EXPOSE 8585 9090
ENTRYPOINT ["java", "-jar", "domain.jar"]