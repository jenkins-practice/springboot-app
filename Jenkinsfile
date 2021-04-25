def foo = "foo 1"

pipeline {

    environment { 
        registry = "ashan97/spring-boot-api-example" 
        registryCredential = 'dockerhub_id' 
        dockerImage = '' 
    }

    agent any

    triggers {
        pollSCM '* * * * *'
    }
    stages {



        stage('Build') {
            steps {
                echo "FOO ------------------------------------> ${foo}"
                echo 'hello world 11'
                sh 'chmod +x gradlew'
                echo 'hello world 22'

                script {
                    foo = "FOO 2"
                }

                // sh '$foo = "foo 2"'
                echo "FOO ------------------------------------> ${foo}"
                // sh './gradlew publish -PfirstParam=100'
                sh './gradlew assemble'
            }
        }
        stage('Test') {
            steps {
                sh './gradlew test'
            }
        }
        stage('Build Docker image') {
            steps {
                echo '********************* current location files ******************'

                echo '******************** docker images *****************'
                sh 'docker images'
                echo '********************* start docker operations ******************'

                // sh './gradlew docker -DfirstParam=100'

                script { 

                    dockerImage = docker.build registry + ":$BUILD_NUMBER" 

                }

            }
        }
        stage('Push Docker image') {
            // environment {
            //     DOCKER_HUB_LOGIN = credentials('docker-hub')
            // }
            steps {
//                 sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
//                 sh './gradlew dockerPush'

              script {

                    docker.withRegistry( '', registryCredential ) {

                        dockerImage.push()

                    }

                }
            }

        }
    }
}

