FROM java:8

EXPOSE 8888

ADD web-backend-sys-0.0.1-SNAPSHOT.jar app.jar
RUN bash -c 'touch /app.jar'

ENTRYPOINT ["java", "-jar", "/app.jar", "--spring.profiles.active=pro"]
