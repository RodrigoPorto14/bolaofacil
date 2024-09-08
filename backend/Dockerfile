FROM ubuntu:latest AS build

RUN apt-get update
RUN apt-get install openjdk-11-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install -DskipTests

FROM openjdk:11

EXPOSE 8080

COPY --from=build /target/bolaofacil-0.0.1-SNAPSHOT.jar bolaofacil.jar

ENTRYPOINT ["java","-jar","/bolaofacil.jar"]