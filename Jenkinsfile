def app=""

pipeline {

    tools {
        maven 'local-maven'
        dockerTool 'local-docker'
    }
    
    agent any

    environment {
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
        REGION = 'us-east-1'
        BUCKET = 'stanley-jenkins-artifacts'
        PROJECT = 'hello-world'
        S3_PATH="${BUCKET}/${PROJECT}/builds/${JOB_NAME}-${BUILD_NUMBER}"
        DOCKER_IMAGE="stanleywxc/${PROJECT}"
    }
    stages {
             
        stage('Setting up environment variables') {
            steps {
                echo "Region: ${env.REGION}"
                echo "S3 Bucket: ${env.BUCKET}"
                echo "Project: ${env.PROJECT}"
                echo "${env.PATH}"
            }
        }

        stage('Build') {
            steps {
                sh 'env sort'
                sh 'mvn clean package -Dmaven.test.skip=true'
                echo "Build stage Finished"
            }
        }

/**
        stage('Test') {
            steps {
                sh 'mvn test'
                echo "Test stage Finished"
            }
        }
**/

        stage('Publish build to S3') {

            steps {
                echo "Publish artifacts to s3: ${env.S3_PATH}"

                pwd(); //Log current directory
                //s3Upload consoleLogLevel: 'INFO', dontSetBuildResultOnFailure: true, dontWaitForConcurrentBuildCompletion: false, entries: [[bucket: env.S3_PATH, excludedFile: '', path: env.PROJECT, flatten: true, gzipFiles: false, keepForever: false, managedArtifacts: false, noUploadOnFailure: true, selectedRegion: env.REGION, showDirectlyInBrowser: false, sourceFile: '**/target/*.jar', storageClass: 'STANDARD', uploadFromSlave: false, useServerSideEncryption: false]], pluginFailureResultConstraint: 'FAILURE', profileName: 'artifact-s3-profile', userMetadata: []
            }
        }

        stage('Build Docker Image') {
            
           
            steps {
                echo "Building docker image: ${env.DOCKER_IMAGE}"
                 
                script {
                    /* This builds the actual image; synonymous to
                    * docker build on the command line */
                    app = docker.build("${env.DOCKER_IMAGE}:${BUILD_NUMBER}", "-f Dockerfile.jenkins .")
                }
            }
        }

        /*
        stage('Test Docker Image') {
            // Ideally, we would run a test framework against our image.
            // For this example, we're using a Volkswagen-type approach ;-)
            steps {
                script {
                    app.inside {
                        sh 'curl localhost:8080/healthz'
                    }
                }
            }
        }*/

        stage('Push Docker image') {
            /* Finally, we'll push the image with two tags:
             * First, the incremental build number from Jenkins
             * Second, the 'latest' tag.
             * Pushing multiple tags is cheap, as all the layers are reused. */
            steps {
                script {
                    docker.withRegistry('https://registry.hub.docker.com', 'my-docker-hub-credentials') {
                        app.push("latest")
                    }
                }
            }
        }
    }
}