#----------------------------------------------
# Step 1: clone the source repo
#----------------------------------------------
FROM alpine/git as clone
ARG REPO_URL=https://c1351f77934cf55b22b3379d8965238bc478e5c6:x-oauth-basic@github.com/stanleywxc/hello-world.git

WORKDIR /app
RUN git clone ${REPO_URL}

#----------------------------------------------
# Step 2: Build the binary
#----------------------------------------------

FROM maven:3.5-jdk-8-alpine as build
ARG project=hello-world
WORKDIR /app
COPY --from=clone /app/${project} /app
RUN mvn install

#----------------------------------------------
# Step 3: Copy the built binary over and build
#         The docker image
#----------------------------------------------
FROM openjdk:8-jre-alpine
ARG artifactid=hello-world
ARG version=0.0.1
ENV jparams="-XX:+UseG1GC"
ENV artifact ${artifactid}-${version}.jar

WORKDIR /app
COPY --from=build /app/target/${artifact} /app
EXPOSE 8080
ENTRYPOINT ["sh", "-c"]
CMD ["java ${jparams} -jar ${artifact}"]