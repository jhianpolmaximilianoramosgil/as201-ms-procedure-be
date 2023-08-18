FROM openjdk:17
VOLUME /tmp
COPY "./target/ms-person-0.0.1-SNAPSHOT.jar" "/app.jar"
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "/app.jar"]