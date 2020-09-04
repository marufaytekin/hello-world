pipeline {

    tools {
        maven 'local-maven'
    }
    agent any

    environment {
        //Use Pipeline Utility Steps plugin to read information from pom.xml into env variables
        REGION = 'us-east-1'
        BUCKET = 'stanley-jenkins-artifacts'
        PROJECT = 'hello-world'
    }
    stages {
             
        stage('Setting up environment variables') {
            steps {
                echo "Region: ${env.REGION}"
                echo "S3 Bucket: env.BUCKET"
                echo "Project: env.PROJECT"
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
            environment {
                S3_PATH="${BUCKET}/${PROJECT}/builds/${JOB_NAME}-${BUILD_NUMBER}"
            }
            steps {
                echo "Publish artifacts to s3: ${env.S3_PATH}"

                pwd(); //Log current directory
                s3Upload consoleLogLevel: 'INFO', dontSetBuildResultOnFailure: true, dontWaitForConcurrentBuildCompletion: false, entries: [[bucket: env.S3_PATH, excludedFile: '', path: env.PROJECT, flatten: true, gzipFiles: false, keepForever: false, managedArtifacts: false, noUploadOnFailure: true, selectedRegion: env.REGION, showDirectlyInBrowser: false, sourceFile: '**/target/*.jar', storageClass: 'STANDARD', uploadFromSlave: false, useServerSideEncryption: false]], pluginFailureResultConstraint: 'FAILURE', profileName: 'artifact-s3-profile', userMetadata: []
            }
        }
    }
}