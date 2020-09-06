#----------------------------------------------
# Step 1: clone the source repo
#----------------------------------------------
FROM alpine/git as clone
ARG TOEKN
ARG REPO_URL=https://${TOKEN}:x-oauth-basic@github.com/stanleywxc/hello-world.git

WORKDIR /app
RUN git clone ${REPO_URL}

#----------------------------------------------
# Step 2: Build the binary
#----------------------------------------------

FROM maven:3.5-jdk-8-alpine as build
ARG PROJECT=hello-world

WORKDIR /app
COPY --from=clone /app/${PROJECT} /app
RUN mvn install

#----------------------------------------------
# Step 3: Copy the built binary over and build
#         The docker image
#----------------------------------------------
FROM openjdk:8-jre-alpine
ARG ARTIFACTID=hello-world
ARG VERSION=0.0.1
ARG JAVA_OPTS="-XX:+UseG1GC"

ENV artifact ${ARTIFACTID}-${VERSION}.jar

WORKDIR /app
COPY --from=build /app/target/${artifact} /app
RUN printf "#!/bin/sh\n\njava ${JAVA_OPTS} -jar /app/${artifact}" >> /start
RUN chmod +x /start
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["/start"]