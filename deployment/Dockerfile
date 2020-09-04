#----------------------------------------------
# Step 1: clone the source repo
#----------------------------------------------
FROM alpine/git as clone
ARG url=git@github.com:stanleywxc/hello-world.git

# Add key
ADD id_rsa /root/.ssh/id_rsa

# Skip Host verification for git
ADD config /root/.ssh/config

# Use git with SSH instead of https
ADD .gitconfig /root/.gitconfig

RUN chmod 600 /root/.ssh/id_rsa

WORKDIR /app
RUN git clone ${url}

#----------------------------------------------
# Step 2: Build the binary
#----------------------------------------------

FROM maven:3.5-jdk-8-alpine as build
ARG project=hello-world
WORKDIR /app
COPY --from=clone /app/${project} /app
RUN mvn install -Dmaven.test.skip=true

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