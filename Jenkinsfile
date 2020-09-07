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

        // Jenkins kubernetes tool
        PATH="/usr/local/bin:" + "$PATH"
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

        stage('Build the binary') {
            steps {
                sh 'env sort'
                sh 'mvn clean package -Dmaven.test.skip=true'
                echo "Build stage Finished"
            }
        }

        stage('Unit Tests') {
            steps {
                sh 'mvn test'
                echo "Test stage Finished"
            }
        }

        // The builds are fine, we need to push it
        // to artifactory store, which is S3
        stage('Publish build to S3') {

            steps {
                echo "Publish artifacts to s3: ${env.S3_PATH}"

                pwd(); //Log current directory
                s3Upload consoleLogLevel: 'INFO', dontSetBuildResultOnFailure: true, dontWaitForConcurrentBuildCompletion: false, entries: [[bucket: env.S3_PATH, excludedFile: '', path: env.PROJECT, flatten: true, gzipFiles: false, keepForever: false, managedArtifacts: false, noUploadOnFailure: true, selectedRegion: env.REGION, showDirectlyInBrowser: false, sourceFile: '**/target/*.jar', storageClass: 'STANDARD', uploadFromSlave: false, useServerSideEncryption: false]], pluginFailureResultConstraint: 'FAILURE', profileName: 'artifact-s3-profile', userMetadata: []
            }
        }
        

        // Build the docker image
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

        // Test the docker image just built
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
        
        // push it to docker registry
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

        // conduct the integeration tests
        stage('Integeration Tests') {
            steps {
                script {
                    echo "pretend we passed integration tests"
                }
            }
        }

        stage('Deploy to Staging') {
            steps {
                script {
                    
                    sh '''
                        namespace="$PROJECT-staging-$BUILD_NUMBER"
                        /bin/bash deployment/kubernetes/kube-deploy.sh $namespace $BUILD_NUMBER
                    '''
                }
            }
        }

        // conduct the integeration tests
        stage('Test on Staging') {
            steps {
                script {
                    sh '''
                        # wait for the deployment successful
                        sleep 120
                        
                        # create a test namespace in kubernetes
                        namespace="$PROJECT-staging-$BUILD_NUMBER"
                        
                        # get the port number
                        port=$(kubectl get svc -n $namespace  -o go-template='{{range .items}}{{range.spec.ports}}{{if .nodePort}}{{.nodePort}}{{\"\\n\"}}{{end}}{{end}}{{end}}')
                    
                        # clean up if any error
                        trap "kubectl delete namespace $namespace" EXIT
                        
                        # test case 1
                        curl -f localhost:$port
                        
                        #test case 2
                        curl -f localhost:$port/healthz
                        
                        # test case 3
                        curl -f localhost:$port/info
                    '''
                }
            }
        }

        // pretend the build has been approved.
        stage('Deploy it to Production') {
            steps {
                script {
                    
                    sh '''
                        # Since I am deploying it into the same kubernetes on my Mac
                        # Need to wait for kube to finish its clean up.
                        sleep 120

                        # prodcution namespace 
                        namespace="$PROJECT-ns"

                        # deploy it to production namespace
                        /bin/bash deployment/kubernetes/kube-deploy.sh $namespace $BUILD_NUMBER
                    '''
                }
            }
        }

        // pretend the build has been approved.
        stage('Test on Production') {
            steps {
                script {
                    sh '''
                        # wait for the deployment finishes
                        sleep 120
                        
                        # productino name space
                        namespace="$PROJECT-ns"
                        
                        # clean up if any error and on exit
                        trap "kubectl delete namespace $namespace" EXIT

                        # get the port number
                        port=$(kubectl get svc -n $namespace  -o go-template='{{range .items}}{{range.spec.ports}}{{if .nodePort}}{{.nodePort}}{{\"\\n\"}}{{end}}{{end}}{{end}}')
                        
                        # test case 1
                        curl -f localhost:$port
                        
                        #test case 2
                        curl -f localhost:$port/healthz
                        
                        # test case 3
                        curl -f localhost:$port/info
                    '''
                }
            }
        }

        // pretend the build has been approved.
        stage('Generate the deployement report') {
            steps {
                script {
                        sh '''
                            # Check if directory exists
                            if [ ! -d "./build-reports"]; then
                                mkdir -p ./build-reports
                            fi
                        '''


                        publishHTML (target : [allowMissing: false,
                         alwaysLinkToLastBuild: true,
                         keepAll: true,
                         includes: '**/*',
                         reportDir: 'build-reports',
                         reportFiles: "${env.PROJECT}-report.html",
                         reportName: "${env.PROJECT} Report",
                         reportTitles: "${env.PROJECT}-report Report"])
                }
            }
        }

        // pretend the build has been approved.
        stage('Send Notifiction about the deployement') {
            steps {
                script {
                    echo "pretend notification being sent"
                    echo "we can config AWS SNS to send text message/emails to the concerned parties"
                }
            }
        }
    }
}