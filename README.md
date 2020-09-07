# Hello World

Using your favorite language (Go, Python, Java, Scala, Bash, etc.), create a hello world web application API
that listens on port 8080 and greets a user with `Hello!` and exposes a health status endpoint.

1. Application url should return a greeting such as `Hello!` as json or plain text (ex: when you open a browser and 
navigate to http://localhost:8080, it should return `Hello!` plain text.)
2. Application should provide a health endpoint (http://localhost:8080/healthz) that returns HTTP status (200 OK)
which indicates health of the application and returns a valid json with the following information:
   - `status`: status of the app: online, success, OK, error, etc.  
   - `version`: running application version (ex: 0.0.1)  
   - `uptime`: time duration or time stamp since the app is running (ex: running since YYYY-MM-DD hh:mm:ss)
  Example: When you open a browser and navigate to http://localhost:8080/healthz it should return:
  ```
  {
    "status": "OK",
    "version": "0.0.1",
    "uptime": "up since 2020-08-04 08:00:05"
  }
  ```
3. What other information would you add to health endpoint json object in step 2? Explain what would be the use case
for that extra information?
4. Create a docker file to build, package, deploy, and run this application locally with Docker.    
5. How would you automate the build/test/deploy process for this application? (a verbal answer is enough. installation of CICD is bonus, not required)
   - What branching strategy would you use for development?
   - What CICD tool/service would you use?
   - What stages would you have in the CICD pipeline?
   - What would be the purpose of each stage in CICD pipeline
  


6. Your solution should include a README explaining how to build and run the application with Docker. We will follow the steps you provide in readme file and execute it to verify.

NOTE: Please submit github repository url for your solution.

### Stanley Wang's solution

1. The restful api is implemented in java via SpringBoot.
2. The hello endpoint ["/"] is in HelloController, and health endpoints ["/healthz", "/info"], are in HealthController

#### [Question 3]:
3. IP addresses and hostname are added into healthz endpoints in case multiple instances are deployed in docker environments, we need a way to identify which instance endpoint we are hitting at.
4. The ["/info"] endpoint returns more info about the running environment, such as java jvm environment variables, build info, host environment variables. Could add some runtime info such as memory usage, CPU usage % etc, but no time to add them.

#### [Question 4]:
Before you run the build below, you need to generate a github oauth access token with public repo permission.
1. Dockerfile is in deployment directory
2. To build the docker image:
```
cd deployment
docker build --build-arg TOKEN=your-github-access-token . -f Dockerfile -t stanleywxc/hello-world:test
```
3. To run the image locally in Docker:
```
docker run --rm -d -p 8080:8080 stanleywxc/hello-world:test
```

#### [Question 5]:
##### - What branching strategy would you use for development?
   I would have 3 major branches: development, staging, and production(master branch), depending on the nature of biz, could have uat(user acceptance branch), feature branches are also very popular in development.
   
##### - What CICD tool/service would you use?
   Depending on the nature of application and deployment environment. If it is in AWS, I would use AWS codedeploy + cloudformation + ECS/EKS, we also can use Jenkins for building artifacts of micro-services builds. 
   
##### - What stages would you have in the CICD pipeline?
     At the high level, CICD piplines are CI Pipeline, which includes stages of Build, Unit Test, Integration Tests and CD Pipeline which includes stages of Review, Staging, Production.
     
     what I have in CICD pipeline:
     1. stage('Build the binary')
     2. stage('Unit Tests') 
     3. stage('Publish build to S3'), served as artifacts storage
     4. stage('Build Docker Image')
     5. stage('Test Docker Image') 
     6. stage('Push Docker image'), push docker image to Docker repo, so it can be consumed by kubernetes
     7. stage('Integeration Tests') If any, we don't have
     8. stage('Deploy to Staging'), using PROJECT-$BUILD_NUMBER as namespace in kube to isolate the environments
     9. stage('Test on Staging') 
     10. stage('Deploy it to Production')
     11. stage('Test on Production')
     12. stage('Generate the deployement report') (The plugin, having problems, still trying to figure out)
     13. stage('Send Notifiction about the deployement')
     
 ##### - What would be the purpose of each stage in CICD pipeline
   -- they are very self explanatory in my above stage labels.
