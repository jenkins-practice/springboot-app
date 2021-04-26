def currentVersion = 0
def verssionIncrement = 0

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

        stage('resource git clone'){

            environment {
                GIT_HUB_LOGIN = credentials('github-id')
            }

            steps{

                sh 'rm -rf resources'
                sh 'rm -rf .git'
                sh 'rm -f env.json'
                sh 'git init'
                sh "git remote add origin https://$GIT_HUB_LOGIN_USR:$GIT_HUB_LOGIN_PSW@github.com/jenkins-practice/resources.git"
                sh 'git fetch'
                sh 'git checkout master'
                sh 'ls'

                script {
                    def envs = readJSON file: "./env.json"
                    currentVersion = envs.app_version
                    verssionIncrement = envs.version_increment
                    currentVersion = currentVersion + verssionIncrement
                }
                echo "github Build version : ${currentVersion}"
            }
        }

        stage('Build') {
            steps {
                echo "Build version : ${currentVersion}"

                echo '********************* current location files ******************'
                sh 'ls'
                
                sh 'chmod +x gradlew'
                
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

                echo '********************* start docker build operations ******************'
                // sh "./gradlew docker -DversionParam=${currentVersion}"

                
                sh "docker build --build-arg JAR_FILE=build/libs/\\*.jar -t ashan97/spring-boot-api-example:latest -t ashan97/spring-boot-api-example:v${currentVersion} ."

                echo '********************* docker images *****************************'
                sh 'docker images'
                
            }
        }
        stage('Push Docker image') {
            environment {
                DOCKER_HUB_LOGIN = credentials('docker-hub')
            }
            steps {
                sh 'docker login --username=$DOCKER_HUB_LOGIN_USR --password=$DOCKER_HUB_LOGIN_PSW'
                // sh './gradlew dockerPush'
                sh "docker push ashan97/spring-boot-api-example:v${currentVersion}"
            }

        }
    }
}

