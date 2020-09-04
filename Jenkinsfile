pipeline {

    agent any

    stages {
        stage('Build') {
            steps {
                git 'https://github.com/stanleywxc/secure-rest-api.git'
                echo pwd()
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

                    s3Upload(bucket:"stanley-jenkins-artifacts", path:'/', includePathPattern:'**/*.jar', workingDir:'target')
                }

            }
        }
    }
}