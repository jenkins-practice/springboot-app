pipeline {
    agent any
    triggers {
        pollSCM '* * * * *'
    }
    stages {
        stage('Build') {
            steps {
                echo 'hello world 11'
                sh 'chmod +x gradlew'
                echo 'hello world 22'
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
            
                sh './gradlew dockerBuild'
            }
        }
        stage('Push Docker image') {
            environment {
                DOCKER_HUB_LOGIN = credentials('docker-hub')
            }
            steps {
                sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
                sh './gradlew dockerPush'
            }
        }
    }
}

