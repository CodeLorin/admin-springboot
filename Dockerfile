FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src

# Build a release artifact.
RUN mvn package -DskipTests
RUN apk add --update font-adobe-100dpi ttf-dejavu fontconfig
EXPOSE 8888

ENTRYPOINT ["java", "-jar", "/app/target/web-backend-sys-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=pro"]
