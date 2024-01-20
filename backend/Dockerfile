FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/bolaofacil-0.0.1-SNAPSHOT.jar bolaofacil.jar
ENTRYPOINT ["java","-jar","/bolaofacil.jar"]