# syntax=docker/dockerfile:1

FROM supervisor-base-image
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
HEALTHCHECK CMD curl -f http://localhost:8080 || exit 1
ENTRYPOINT ["java","-jar","/app.jar"]