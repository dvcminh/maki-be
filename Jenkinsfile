pipeline {
    agent any
    tools {
        maven 'maven_tool'
    }
    stages {
        stage('Build Maven') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Build docker image') {
            steps {
                sh 'docker build -t maki .'
            }
        }
        stage('Push image to hub') {
            steps {
                withCredentials([string(credentialsId: 'dockerhub-pwd', variable: 'dockerhubpwd')]) {
                    sh 'docker login -u test -p ${test}'
                }
                sh 'docker tag maki ducminh210503/maki'
                sh 'docker push ducminh210503/maki'
            }
        }
    }
}