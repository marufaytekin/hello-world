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

        stage('Test') {
            steps {
                sh 'mvn test'
                echo "Test stage Finished"
            }
        }

        stage('Publish build to S3') {

            steps {
                echo "Publish artifacts to s3: ${env.S3_PATH}"

                pwd(); //Log current directory
                s3Upload consoleLogLevel: 'INFO', dontSetBuildResultOnFailure: true, dontWaitForConcurrentBuildCompletion: false, entries: [[bucket: env.S3_PATH, excludedFile: '', path: env.PROJECT, flatten: true, gzipFiles: false, keepForever: false, managedArtifacts: false, noUploadOnFailure: true, selectedRegion: env.REGION, showDirectlyInBrowser: false, sourceFile: '**/target/*.jar', storageClass: 'STANDARD', uploadFromSlave: false, useServerSideEncryption: false]], pluginFailureResultConstraint: 'FAILURE', profileName: 'artifact-s3-profile', userMetadata: []
            }
        }
        

        stage('Build Docker Image') {
            
           
            steps {
                echo "Building docker image: ${env.DOCKER_IMAGE}"
                 
                script {
                    /* This builds the actual image; synonymous to
                    * docker build on the command line */
                    app = docker.build("${env.DOCKER_IMAGE}", "-f Dockerfile.jenkins .")
                }
            }
        }

        stage('Test Docker Image') {
        
            steps {
                script {
                    // piece of shit Jenkins unbelievable! app.inside() dosn't work!
                    // hacking it.
                    sh '''
                        # Generate a random port # to avoid conflict
                        port=$(while :; do ran=$RANDOM; ((ran < 32760)) && echo $((ran + 20000)) && break; done)
                        
                        # run the newly built image
                        docker run --rm -d -p $port:8080 --name $PROJECT-$BUILD_NUMBER $DOCKER_IMAGE
                        
                        # sleep 5 seconds to wait it up, sometimes it takes more than 15 seconds
                        sleep 20
                        
                        # stop the running container on exit
                        trap "docker stop $PROJECT-$BUILD_NUMBER" EXIT
                        
                        # anything other than 200 code will fail and wait for maximum 15 seconds
                        curl -f --connect-timeout 60 --max-time 60 localhost:$port/healthz
                    '''
                }
            }
        }
        
        stage('Push Docker image') {
            steps {
                
                script {
                    // Freaking withRegistry doesn't work!
                    withCredentials([usernamePassword( credentialsId: 'my-docker-hub-credentials', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) 
                    {
                        sh "docker login -u ${USERNAME} -p ${PASSWORD}"
                        
                        app.push("${BUILD_NUMBER}")
                        app.push("latest")
                    }
                }
            }
        }
    }
}