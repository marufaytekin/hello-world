FROM ubuntu:latest
MAINTAINER Rajesh Kanuri
RUN apt-get update -y && \
    apt-get install -y  default-jre && \
    apt-get install -y  default-jdk
RUN mkdir -p /home/hello
#----UNCOMMENT below lines if you want to run as non-root user, you should add that user to docker group-- 
#RUN adduser  $USER 
#RUN echo hello:hello | chpasswd
#USER hello
ENV HOME /home/hello
ADD build/libs/hello-world-0.0.1-SNAPSHOT.jar $HOME/
CMD java -jar $HOME/*.jar
