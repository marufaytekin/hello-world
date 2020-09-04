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
3. IP addresses and hostname are added into healthz endpoints in case it is deployed in multiple instances and in docker environments, we need a way to identify which instance endpoint we are hitting at.
4. The ["/info"] endpoint returns more info about the running environment, such as java jvm environment variables, build info, host environment variables

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

