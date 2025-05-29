pipeline {
    agent any

    environment {
        DOCKER_REGISTRY = 'your-dockerhub-username'
        IMAGE_TAG = "${env.BUILD_NUMBER}"
        KUBE_CONFIG_CREDENTIALS_ID = 'kubeconfig-id' // Jenkins credential for kubeconfig
    }

    stages {

        stage('Checkout') {
            steps {
                git 'https://github.com/your-org/ecommerce-platform.git'
            }
        }

        stage('Build & Push Docker Images') {
            parallel {
                stage('Build Product Image') {
                    steps {
                        dir('product') {
                            script {
                                docker.build("${DOCKER_REGISTRY}/product:${IMAGE_TAG}").push()
                            }
                        }
                    }
                }
                stage('Build Order Image') {
                    steps {
                        dir('order') {
                            script {
                                docker.build("${DOCKER_REGISTRY}/order:${IMAGE_TAG}").push()
                            }
                        }
                    }
                }
                stage('Build Customer Image') {
                    steps {
                        dir('customer') {
                            script {
                                docker.build("${DOCKER_REGISTRY}/customer:${IMAGE_TAG}").push()
                            }
                        }
                    }
                }
                stage('Build Inventory Image') {
                    steps {
                        dir('inventory') {
                            script {
                                docker.build("${DOCKER_REGISTRY}/inventory:${IMAGE_TAG}").push()
                            }
                        }
                    }
                }
                stage('Build Payment Image') {
                    steps {
                        dir('payment') {
                            script {
                                docker.build("${DOCKER_REGISTRY}/payment:${IMAGE_TAG}").push()
                            }
                        }
                    }
                }
                stage('Build API Gateway') {
                    steps {
                        dir('apigateway') {
                            script {
                                docker.build("${DOCKER_REGISTRY}/apigateway:${IMAGE_TAG}").push()
                            }
                        }
                    }
                }
                stage('Build Eureka Server') {
                    steps {
                        dir('eureka-server') {
                            script {
                                docker.build("${DOCKER_REGISTRY}/eureka-server:${IMAGE_TAG}").push()
                            }
                        }
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                withCredentials([file(credentialsId: "${KUBE_CONFIG_CREDENTIALS_ID}", variable: 'KUBECONFIG_FILE')]) {
                    sh '''
                    export KUBECONFIG=$KUBECONFIG_FILE

                    # Replace image tags in the Kubernetes YAML dynamically
                    sed -i "s|product:latest|${DOCKER_REGISTRY}/product:${IMAGE_TAG}|g" k8s/kubernetes-deployment.yaml
                    sed -i "s|order:latest|${DOCKER_REGISTRY}/order:${IMAGE_TAG}|g" k8s/kubernetes-deployment.yaml
                    sed -i "s|customer:latest|${DOCKER_REGISTRY}/customer:${IMAGE_TAG}|g" k8s/kubernetes-deployment.yaml
                    sed -i "s|inventory:latest|${DOCKER_REGISTRY}/inventory:${IMAGE_TAG}|g" k8s/kubernetes-deployment.yaml
                    sed -i "s|payment:latest|${DOCKER_REGISTRY}/payment:${IMAGE_TAG}|g" k8s/kubernetes-deployment.yaml
                    sed -i "s|apigateway:latest|${DOCKER_REGISTRY}/apigateway:${IMAGE_TAG}|g" k8s/kubernetes-deployment.yaml
                    sed -i "s|eureka-server:latest|${DOCKER_REGISTRY}/eureka-server:${IMAGE_TAG}|g" k8s/kubernetes-deployment.yaml

                    # Apply to Kubernetes
                    kubectl apply -f k8s/kubernetes-deployment.yaml
                    '''
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}
