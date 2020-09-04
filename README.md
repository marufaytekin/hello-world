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
1. Dockerfile is in deployment directory
2. To build the docker image:
```
cd deployment
docker build . -f Dockerfile -t stanleywxc/hello-world:test
```
3. To run the image locally in Docker:
```
docker run -d -p 8080:8080 stanleywxc/hello-world:test
```

#### [Question 5]:
##### - What branching strategy would you use for development?
   I would have 3 major branches: development, staging, and production(master branch), depending on the nature of biz, could have uat(user acceptance branch), feature branches are also very popular in development.
   
##### - What CICD tool/service would you use?
   Depending on the nature of application and deployment environment. If it is in AWS, I would use AWS codedeploy + cloudformation + ECS/EKS, we also can use Jenkins for building artifacts of micro-services builds. 
   
##### - What stages would you have in the CICD pipeline?
     At the high level, CICD piplines are CI Pipeline, which includes stages of Build, Unit Test, Integration Tests and CD Pipeline which includes stages of Review, Staging, Production.
     
     At minimum in production deployment, there should be 'build', 'test', (or pre-built artifacts), 'deploy', 'check deployment live status', 'switch traffic', 'wait old traffic dry', 'stop old instances',      'notification deployment status'. 
     If there are errors happened after deployment and timeout during 'check deployment live status', we should 'rollback' to old deployment and mark this deployment fail. 
     
 ##### - What would be the purpose of each stage in CICD pipeline
   - stage 'build': to build artifacts, or pull the code repo to run the app
   - stage 'test': to verify the build is functioning as it supposes to, specially the test cases coverages should be sufficient enough to ensure the quality of deployment. This stage includes unit test and integration test.
   - stage 'deploy': the actual deployment, either deploy into docker/kubernetes, or standalone servers/clusters.
   - stage 'check deployment live status': this stage is to check if the deployment is functionning before we switch the traffic to the new deployment.
   - stage 'switch traffic': switch the traffic, and wait the traffic to old deployment drying out.
   - stage 'stop old instances': after we see the old traffic dried out and the deployment is working, we need to stop the old instances.
   - stage 'rollback', rollback to old deployment if the new deployment having problems.
