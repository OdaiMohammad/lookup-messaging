# syntax=docker/dockerfile:1

FROM openjdk:8
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
RUN apt-get update -y
RUN apt-get install -y apt-utils
RUN curl -fsSL https://get.docker.com -o get-docker.sh
RUN sh get-docker.sh
HEALTHCHECK CMD curl -f http://localhost:8080 || exit 1
ENTRYPOINT ["java","-jar","/app.jar"]