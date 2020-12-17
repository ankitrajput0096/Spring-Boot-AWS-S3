# Base Image ubuntu
FROM ubuntu:18.04

# Installing dependencies
RUN apt-get update
RUN apt-get install -y openjdk-8-jdk maven 

# coping the required application files
COPY . /s3_spring_boot_app
WORKDIR /s3_spring_boot_app/simplestorageservice

# building the spring boot app
RUN mvn clean install

WORKDIR /s3_spring_boot_app/simplestorageservice/target


# running the spring boot app
CMD ["java", "-jar", "simplestorageservice-0.0.1-SNAPSHOT.jar"]

