pipeline {

agent any

```
environment {
    DOCKER_IMAGE = "tanushri1510/tanu"
    DOCKER_TAG = "latest"
}

stages {

    stage('Clone Repository') {
        steps {
            git branch: 'master', url: 'https://github.com/Delta-Student27/UrlShortner.git'
        }
    }

    stage('Build with Maven') {
        steps {
            bat 'mvn clean package -DskipTests'
        }
    }

    stage('Build Docker Image') {
        steps {
            bat 'docker build -t $DOCKER_IMAGE:$DOCKER_TAG .'
        }
    }

    stage('Login to DockerHub') {
        steps {
            withCredentials([usernamePassword(
                credentialsId: 'dockerhub-credentials',
                usernameVariable: 'tanushri1510',
                passwordVariable: 'Jayhind@1508'
            )]) {
                bat 'echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin'

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


    stage('Push Docker Image') {
        steps {
            bat 'docker push $DOCKER_IMAGE:$DOCKER_TAG'
        }
    }

    stage('Deploy Container') {
        steps {
            bat '''
            docker stop url-container || true
            docker rm url-container || true

            docker run -d -p 8083:8083 \
            -e DB_URL=jdbc:mysql://crossover.proxy.rlwy.net:30764/railway\
            -e DB_USERNAME=root\
            -e DB_PASSWORD=OWcNreqazHRHPiytrvnqfhAGSPuUOqvx\
            --name tanu \
            $DOCKER_IMAGE:$DOCKER_TAG
            '''
        }
    }
}

post {
    success {
        echo '✅ Build & Deployment Successful!'
    }
    failure {
        echo '❌ Pipeline Failed!'
    }
}
```
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
