FROM eclipse-temurin
ARG JAR_FILE=target/jira-1.0.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]