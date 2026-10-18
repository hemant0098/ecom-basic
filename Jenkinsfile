
pipeline {
    agent any

    environment {
        DOCKER_USERNAME = 'hemantdevgan'
        IMAGE_TAG = 'v1.5'
    }

    stages {
        stage('Build Images') {
            steps {
                echo 'Building images'

                sh 'docker build -f api-gateway/Dockerfile -t api-gateway:v1.0 .'
                sh 'docker build -f discovery-server/Dockerfile -t ecom-discovery-server:v1.0 .'
                sh 'docker build -f catalog-service/Dockerfile -t ecom-catalog-service:v1.0 .'
                sh 'docker build -f notification-service/Dockerfile -t ecom-notification-service:v1.0 .'
                sh 'docker build -f order-service/Dockerfile -t ecom-order-service:v1.0 .'
                sh 'docker build -f payment-service/Dockerfile -t ecom-payment-service:v1.0 .'
                sh 'docker build -f product-service/Dockerfile -t ecom-product-service:v1.0 .'
                sh 'docker build -f user-service/Dockerfile -t ecom-user-service:v1.0 .'
            }
        }

        stage('Tag Images') {
            steps {
                echo 'Tagging images'

                sh 'docker tag api-gateway:v1.0 $DOCKER_USERNAME/api-gateway:$IMAGE_TAG'
                sh 'docker tag ecom-discovery-server:v1.0 $DOCKER_USERNAME/discovery-server:$IMAGE_TAG'
                sh 'docker tag ecom-catalog-service:v1.0 $DOCKER_USERNAME/catalog-service:$IMAGE_TAG'
                sh 'docker tag ecom-notification-service:v1.0 $DOCKER_USERNAME/notification-service:$IMAGE_TAG'
                sh 'docker tag ecom-order-service:v1.0 $DOCKER_USERNAME/order-service:$IMAGE_TAG'
                sh 'docker tag ecom-payment-service:v1.0 $DOCKER_USERNAME/payment-service:$IMAGE_TAG'
                sh 'docker tag ecom-product-service:v1.0 $DOCKER_USERNAME/product-service:$IMAGE_TAG'
                sh 'docker tag ecom-user-service:v1.0 $DOCKER_USERNAME/user-service:$IMAGE_TAG'
            }
        }

        stage('Docker Login') {
            steps {
                withCredentials([
                    string(
                        credentialsId: 'dockerhub-token',
                        variable: 'DOCKERHUB_TOKEN'
                    )
                ]) {
                    sh '''
                        set +x
                        echo "$DOCKERHUB_TOKEN" |
                        docker login -u "$DOCKER_USERNAME" --password-stdin
                    '''
                }
            }
        }

        stage('Push Images') {
            steps {
                echo 'Pushing images to Docker Hub'

                sh 'docker push $DOCKER_USERNAME/api-gateway:$IMAGE_TAG'
                sh 'docker push $DOCKER_USERNAME/discovery-server:$IMAGE_TAG'
                sh 'docker push $DOCKER_USERNAME/catalog-service:$IMAGE_TAG'
                sh 'docker push $DOCKER_USERNAME/notification-service:$IMAGE_TAG'
                sh 'docker push $DOCKER_USERNAME/order-service:$IMAGE_TAG'
                sh 'docker push $DOCKER_USERNAME/payment-service:$IMAGE_TAG'
                sh 'docker push $DOCKER_USERNAME/product-service:$IMAGE_TAG'
                sh 'docker push $DOCKER_USERNAME/user-service:$IMAGE_TAG'
            }
        }

        stage('Database Setup') {
            steps {
                echo 'Creating database resources'

                sh 'kubectl apply -f k8s/postgres-statefulset.yml'
                sh 'kubectl apply -f k8s/postgres-service.yml'
            }
        }

        stage('Deploy Services') {
            steps {
                echo 'Deploying service deployments'

                sh 'kubectl apply -f k8s/discovery-server-deployment.yml'
                sh 'kubectl apply -f k8s/api-gateway-deployment.yml'
                sh 'kubectl apply -f k8s/catalog-service-deployment.yml'
                sh 'kubectl apply -f k8s/notification-service-deployment.yml'
                sh 'kubectl apply -f k8s/order-service-deployment.yml'
                sh 'kubectl apply -f k8s/payment-service-deployment.yml'
                sh 'kubectl apply -f k8s/product-service-deployment.yml'
                sh 'kubectl apply -f k8s/user-service-deployment.yml'

                echo 'Deploying services'

                sh 'kubectl apply -f k8s/discovery-server-service.yml'
                sh 'kubectl apply -f k8s/api-gateway-service.yml'
                sh 'kubectl apply -f k8s/catalog-service.yml'
                sh 'kubectl apply -f k8s/notification-service.yml'
                sh 'kubectl apply -f k8s/order-service.yml'
                sh 'kubectl apply -f k8s/payment-service.yml'
                sh 'kubectl apply -f k8s/product-service.yml'
                sh 'kubectl apply -f k8s/user-service.yml'
            }
        }

        stage('Verify Deployment') {
            steps {
                echo 'Checking Kubernetes resources'

                sh 'kubectl get deployments'
                sh 'kubectl get pods'
                sh 'kubectl get services'
            }
        }
    }

    post {
        success {
            echo 'Pipeline completed successfully'
        }

        failure {
            echo 'Pipeline failed. Check the stage logs.'
        }
    }
}
