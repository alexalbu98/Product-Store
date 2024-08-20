pipeline {
    agent any
    stages {
        stage("Compile") {
            steps {
                sh "./mvnw clean compile"
            }
        }
        stage("Functional Testing") {
            steps {
                sh "./mvnw clean test"
            }
        }
        stage("Static Code Analysis") {
            steps {
                sh "./mvnw clean verify"
            }
        }
        stage("Deploy") {
            when {
                branch 'dev'
            }
            steps {
                sh "./mvnw clean compile"
                sh "echo 'Deploying Code...'"
            }
        }
    }
}