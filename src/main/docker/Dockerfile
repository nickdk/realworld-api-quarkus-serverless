## Stage 1 : build with maven builder image with native capabilities
FROM quay.io/quarkus/centos-quarkus-maven:19.3.1-java8 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
USER root
RUN chown -R quarkus /usr/src/app
USER quarkus

#Waiting for gRPC support to be able to use Firestore and other gRPC dependent libraries in native mode
#See: https://github.com/quarkusio/quarkus/issues/820
#RUN mvn -B -f /usr/src/app/pom.xml -Pnative clean package

RUN mvn -B -f /usr/src/app/pom.xml clean package

## Stage 2 : create the docker final image
FROM registry.access.redhat.com/ubi8/ubi-minimal
WORKDIR /work/
COPY --from=build /usr/src/app/target/*-runner /work/application
RUN chmod 775 /work
EXPOSE 8080
CMD ["./application", "-Dquarkus.http.host=0.0.0.0"]