FROM openjdk:11
VOLUME /tmp
EXPOSE 8080
ADD ./target/bolaofacil-0.0.1-SNAPSHOT.jar bolaofacil.jar
ADD ./keystore.p12 keystore.p12
ENTRYPOINT ["java","-jar","/bolaofacil.jar"]