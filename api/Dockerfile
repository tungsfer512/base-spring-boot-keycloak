FROM openjdk:17.0.2-bullseye

ARG POSTGRES_USER=postgres
ARG POSTGRES_PASSWORD=postgres
ARG POSTGRES_DB=postgres

WORKDIR /api

RUN apt update
RUN apt upgrade -y
RUN apt install -y sshpass sudo nano

COPY . .

RUN ./mvnw clean install -DskipTests -q

EXPOSE 8080

CMD ["./mvnw", "spring-boot:run"]