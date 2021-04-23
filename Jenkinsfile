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
                sh 'chmod 664 /var/run/docker.sock'
                sh './gradlew docker'
            }
        }
        stage('Push Docker image') {
            environment {
                DOCKER_HUB_LOGIN_USER = credentials('ashan97')
                DOCKER_HUB_LOGIN_PSW = credentials('971521813')
            }
            steps {
                sh 'docker login --username=$DOCKER_HUB_LOGIN_USER --password=$DOCKER_HUB_LOGIN_PSW'
                sh './gradlew dockerPush'
            }
        }
    }
}
// pipeline {
//     agent any
//
//     triggers {
//         pollSCM '* * * * *'
//     }
//     stages {
//         stage('Build') {
//             steps {
//                 echo 'hello world'
//                 sh 'chmod +x gradlew'
//                 echo 'hello world'
//                 sh './gradlew assemble'
//             }
//         }
//         stage('Test') {
//             steps {
//                 sh './gradlew test'
//             }
//         }
//     }
// }
