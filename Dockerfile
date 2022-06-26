FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests

EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app/target/web-backend-sys-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=pro"]
