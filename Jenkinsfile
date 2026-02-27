pipeline {
    agent any

    tools {
        jdk 'JDK21'
        maven '3.9.12'
    }

    stages {

        stage('Build') {
            steps {
                echo "Building branch: ${env.BRANCH_NAME}"
                bat 'mvn clean package -DskipTests'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    }

    post {
        success {
            echo "✅ Build successful for ${env.BRANCH_NAME}"
        }
        failure {
            echo "❌ Build failed for ${env.BRANCH_NAME}"
        }
    }
}