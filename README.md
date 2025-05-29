# edureka_tcs
spring boot 3.X+java 17
ecommerce using docker compose+kafka+mongodb+basicauth

#docker
1) docker desktop - install
2) package individual apis - mvn clean package -DskipTests
3) run in root - docker-compose up --build

#kubernetes
1) run command - kubectl apply -f k8s/kubernetes-deployment.yaml

#Jenkins - Install Required Jenkins Plugins:
1) Docker Pipeline
2) Kubernetes CLI Plugin
3) Git Plugin
4) Credentials Binding Plugin
5) Add Jenkins Credentials:
6) DockerHub Credentials: If your registry is private.
7) Kubeconfig File: Add your kubeconfig file as a "Secret File" and give it the ID kubeconfig-id.
8) GitHub Webhook (optional):
9) Set up GitHub → Settings → Webhooks to trigger Jenkins on push events.