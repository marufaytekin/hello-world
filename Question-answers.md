3)What other information would you add to health endpoint json object in step 2? 
  Explain what would be the use case for that extra information?
  
 	I would always recommend to map an end-poind to number of instnces and the version information on every instance.
 	Use Case: it is always helpful for versioning validation scripts, where a single API can gather all the 
                  information about artifact versions. It will prevent version mismatching.
		  
5)How would you automate the build/test/deploy process for this application? 
(a verbal answer is enough. installation of CICD is bonus, not required)
	I would suggest to use any CI server which will support multi-branch pipeline. 
	I will automate using Jenkins Multi-branch pipeline by writing Jenkinsfile.

	What branching strategy would you use for development?

	I will restrict "Master" branch from merging code directly without pull-request.
	I will make sure every other branch will have respective ticket/task number.
	I will push the coomits with  ticket/task number, so that code-review will be easy.

	What CICD tool/service would you use?
	
	Jenkins is my preferred CI/CD tool. 
	
	What stages would you have in the CICD pipeline? What would be the purpose of each stage in CICD pipeline?
	
	Pipeline configuration:
	I will enable the hooks to trigger the Jenkins pipeline whenever a new commit occured.
	I will also check clean-before checkout in my pipeline configuration.
	I will integrate Jenkins with bug-tracking tool like Jira.
	The following staages will be part of my mylti-branch pipeline.
		1) Git checkout: the code from certain brnach will be cloned into  jenkins Workspace
		2) Build: the code will get built by using Gradle plugin.
		3) Verify: JUNIT test cases will run.
		4) Quality Gates: I will integrate quality gates to audit code coverage, test coverage.
		5) pull request: I will put a condition if the branch is Master or release branch , skip this step. 
		   all other branches will prompt user with option to create a pull request to master. 
		6) Docker image: 
	   		if the branch is Master or release branch: build docker image.
       			other branches: skip
		7) Publish:
	   		if the branch is Master or release branch: publish to Docker Registry.
       			other branches: skip
		8) Deploy: I will chose between parallel and single deployment strategies  based on type of 
		   deployment evironments.  
	   
