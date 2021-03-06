def currentVersion = 0
def verssionIncrement = 0
def envs = ""

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
                sh "git remote add origin https://$GIT_HUB_LOGIN_USR:$GIT_HUB_LOGIN_PSW@${env.github_resource_url}"
                sh 'git fetch'
                sh 'git checkout master'
                sh 'ls'

                script {
                    envs = readJSON file: "${env.github_springboot_env_file}"
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

                
                sh "docker build --build-arg JAR_FILE=build/libs/\\*.jar -t ${env.dokcerhub_springboot_repository}/${env.dokcerhub_springboot_tagname}:latest -t ${env.dokcerhub_springboot_repository}/${env.dokcerhub_springboot_tagname}:v${currentVersion} ."

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
                sh "docker push ${env.dokcerhub_springboot_repository}/${env.dokcerhub_springboot_tagname}:v${currentVersion}"
            }

        }
        stage('Commit version to git') {

            environment {
                GIT_HUB_LOGIN = credentials('github-id')
            }

            steps {

                sh "rm -f ${env.github_springboot_env_file}"
                script{
                    
                        envs['app_version'] = currentVersion
                        writeJSON file: "${env.github_springboot_env_file}", json: envs
                      
                        
                }

                sh "git config --global user.email ${env.github_resource_email}"
                sh "git config --global user.name $GIT_HUB_LOGIN_USR"


                echo 'git status : '
                sh 'git status'
                sh "git add ${env.github_springboot_env_file}"
                // sh 'git stage'
                sh "git commit -m \"push changes of ${env.github_springboot_env_file}.\" "
                sh 'git push'
            }
        }
    }
}

