FROM openjdk:17-jdk-slim
COPY target/jiRush.jar ./jiRush.jar
COPY resources ./resources
COPY config ./config
EXPOSE 8080
ENTRYPOINT ["java","-jar","/jiRush.jar", "--spring.profiles.active=prod"]