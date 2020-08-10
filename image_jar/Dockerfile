FROM ubuntu:latest
RUN apt-get update -y && \
    apt-get install -y unzip wget && \
    apt-get install -y  default-jre && \
    apt-get install -y  default-jdk
RUN wget https://services.gradle.org/distributions/gradle-6.5.1-bin.zip && \
    mkdir -p /opt/gradle && \
    unzip -d /opt/gradle gradle-6.5.1-bin.zip && \
    export PATH=$PATH:/opt/gradle/gradle-6.5.1/bin
RUN mkdir -p /home/hello
ENV HOME /home/hello
WORKDIR $HOME/hello-world
CMD umask 227 $HOME/hello-world | export PATH=$PATH:/opt/gradle/gradle-6.5.1/bin | ./gradlew clean build bootJar
