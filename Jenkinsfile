pipeline {
    agent {label 'java-docker'}
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
                sh "./mvnw site"
                publishHTML (target: [
                  reportDir: 'target/site/',
                  reportFiles: 'pmd.html',
                  reportName: "PMD Report"
                ])
                publishHTML (target: [
                  reportDir: 'target/site/jacoco',
                  reportFiles: 'index.html',
                  reportName: "Jacoco Report"
                ])
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