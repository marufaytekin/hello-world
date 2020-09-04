pipeline {

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

                withAWS(region:'us-east-1',profile:'default') {

                    s3Upload(bucket: "${BUCKET}", path: "${PROJECT}/", includePathPattern: "**/*.jar", workingDir: "target")
                }
            }
        }
    }
}