pipeline {
    agent any
    stages {
        stage("Compile") {
            steps {
                sh "./mvnw clean compile"
            }
        }
        stage("Testing") {
            steps {
                    sh "./mvnw clean test"
            }
        }
        stage("Test coverage") {
            steps {
                    sh "./mvnw clean verify"
            }
        }
    }
}