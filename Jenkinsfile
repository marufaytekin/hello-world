pipeline {

    tools {
        maven 'local-maven'
    }
    agent any

    stages {
             
        stage('Setting up environment variables') {
            steps {
                script {
                    def REGION = 'us-east-1'
                    def BUCKET = 'stanley-jenkins-artifacts'
                    def PROJECT = 'hello-world'
                }
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

                echo "Publish artifacts to s3"

                pwd(); //Log current directory
                s3Upload consoleLogLevel: 'INFO', dontSetBuildResultOnFailure: true, dontWaitForConcurrentBuildCompletion: false, entries: [[bucket: 'stanley-jenkins-artifacts', excludedFile: '', flatten: false, gzipFiles: false, keepForever: false, managedArtifacts: false, noUploadOnFailure: true, selectedRegion: 'us-east-1', showDirectlyInBrowser: false, sourceFile: '**/target/*.jar', storageClass: 'STANDARD', uploadFromSlave: false, useServerSideEncryption: false]], pluginFailureResultConstraint: 'FAILURE', profileName: 'artifact-s3-profile', userMetadata: []
            }
        }
    }
}