PRE_REQUISITES:

	1.Docker
	2.Git
	
BUILD STEPS: 
	
	1) clone the github repository into your loacal
	   
	   command: git clone -b $BRANCH_NAME $REPO
	 
	2) go to project root directory and execute the following command to build docker image with 
	   to provision the container for building the application
	   
	   command: cd hello-world
	   command: chmod +x gradlew
	   command: docker build -t gradle:latest image_jar/
	   
	3) Provision a container with the above image. container will build the jar file and build directory 
	   will be copied into project root directory. This step will take 2-3 minutes to generate Jar file.
	   
	   Directory: project root(hello-world) due to bind mount.
	   command: docker run -dit --mount type=bind,source="$(pwd)",target=/home/hello/hello-world --name build gradle:latest
	   
	   Note: After the build execution, the container will exit because there will be no process running inside the container
	   
    	4) Validation: before you proceed to deployment step please validate build artifact. 
        
           command: ls build/libs/
           output:  build/libs/hello-world-0.0.1-SNAPSHOT.jar
       		
	   
	 
DEPLOYMENT STEPS:
    
    	1) build a deployment image by executing below command from project root directory 
	    command: docker build -t hello:latest .
	
	2) Deploy the image using below command
	  
	    command: docker run -dit -p 8080:8080 --name hello hello:latest
		
VALIDATION STEPS:
	 
    1) container 	 
	   command: docker ps

    2) Open your prowser:
      
 	   Navigate to: http://localhost:8080/
	   
	   Note: For remote servers , make sure you opened 8080 for TCP, then validate http://<yourDNS>:8080/
	   
	   Alternatively , you can try curl or elinks requests.
	   
	   
      	   
	   
	  
	   
	   
	    
 	  
	   



