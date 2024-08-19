pipeline {
    agent any
    stages {
        stage("Compile") {
            steps {
                sh "./mvnw compile"
            }
        }
        stage("Testing") {
            steps {
                    sh "./mvnw test"
            }
        }
    }
}