

#  Jenkins – Docker – Kubernetes CI/CD Demo

##  Overview

This project demonstrates a complete **CI/CD pipeline** using:

* Jenkins
* Docker
* Kubernetes

The pipeline automates:

* Building a Java application
* Creating a Docker image
* Pushing it to Docker Hub
* Deploying it to Kubernetes

---

#  Prerequisites

* Install Docker Desktop
* Install Java (JDK 17)
* Install Maven
* Install Jenkins

---

#  Step 1: Install Docker Desktop

Download and install Docker Desktop from the official website.

---

#  Step 2: Enable Kubernetes in Docker

1. Open Docker Desktop
2. Go to **Settings **
3. Select **Kubernetes tab**
4. Enable **Kubernetes**
5. Click **Apply & Restart**

 This creates a **single-node Kubernetes cluster**

---

#  Step 3: Install Jenkins Plugins

In Jenkins:

* Install **Docker Plugin**
* Install **Docker Pipeline Plugin**

---

#  Step 4: Create Java Application

###  Project Folder

```
simple-java-app
```

###  App.java

```java
public class App {
 public static void main(String[] args) throws Exception {
  System.out.println("Java CI/CD Application Started...");
  while (true) {
   Thread.sleep(5000);
   System.out.println("Hello from Java CI/CD Pipeline!");
  }
 }
}
```

###  pom.xml

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
 http://maven.apache.org/xsd/maven-4.0.0.xsd">

 <modelVersion>4.0.0</modelVersion>
 <groupId>com.demo</groupId>
 <artifactId>simple-java-app</artifactId>
 <version>1.0</version>

 <build>
  <plugins>
   <plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.10.1</version>
    <configuration>
     <source>17</source>
     <target>17</target>
    </configuration>
   </plugin>
  </plugins>
 </build>
</project>
```

---

#  Step 5: Build Locally

```bash
mvn clean package
```

 Output:

```
target/simple-java-app-1.0.jar
```

---

#  Step 6: Create Dockerfile

```dockerfile
FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY target/simple-java-app-1.0.jar app.jar
CMD ["java", "-jar", "app.jar"]
```

---

#  Step 7: Push Code to GitHub

```bash
git init
git add .
git commit -m "Initial Java CI/CD commit"
git branch -M main
git remote add origin https://github.com/your-reponame/simple-java-app.git
git push -u origin main
```

---

#  Step 8: Kubernetes Deployment

###  deployment.yaml

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
 name: java-app

spec:
 replicas: 2
 selector:
  matchLabels:
   app: java-app

 template:
  metadata:
   labels:
    app: java-app
  spec:
   containers:
   - name: java-app
     image: <dockerhub-username>/java-app:latest
---
apiVersion: v1
kind: Service
metadata:
 name: java-service

spec:
 type: NodePort
 selector:
  app: java-app
 ports:
 - port: 80
   targetPort: 8080
   nodePort: 30008
```

Push it:

```bash
git add deployment.yaml
git commit -m "Added Kubernetes deployment file"
git push
```

---

#  Step 9: Configure Jenkins

* Run Jenkins
* Install **Kubernetes CLI Plugin**

---

#  Step 10: Add DockerHub Credentials

In Jenkins:

**Manage Jenkins → Manage Credentials → Global → Add Credentials**

| Field    | Value                  |
| -------- | ---------------------- |
| Kind     | Username with password |
| Username | DockerHub username     |
| Password | DockerHub password     |
| ID       | dockerhub-creds        |

---

#  Step 11: Jenkins Pipeline

###  Pipeline Script

```groovy
pipeline {
 agent any

 environment {
  DOCKER_IMAGE = "yourdockerhubusername/java-app:latest"
 }

 stages {

  stage('Clone Repository') {
   steps {
    git branch: 'main',
    url: 'https://github.com/your-reponame/simple-java-app.git'
   }
  }

  stage('Build with Maven') {
   steps {
    bat 'mvn clean package'
   }
  }

  stage('Build Docker Image') {
   steps {
    bat "docker build -t %DOCKER_IMAGE% ."
   }
  }

  stage('Push to DockerHub') {
   steps {
    withCredentials([usernamePassword(
     credentialsId: 'dockerhub-creds',
     usernameVariable: 'DOCKER_USER',
     passwordVariable: 'DOCKER_PASS'
    )]) {
     bat """
     docker login -u %DOCKER_USER% -p %DOCKER_PASS%
     docker push %DOCKER_IMAGE%
     """
    }
   }
  }

  stage('Deploy to Kubernetes') {
   steps {
    bat 'kubectl apply -f deployment.yaml'
   }
  }
 }

 post {
  success {
   echo 'CI/CD Pipeline executed successfully!'
  }
  failure {
   echo 'Pipeline failed. Please check logs.'
  }
 }
}
```

---

#  Step 12: Verify Deployment

```bash
kubectl get svc
```

✔ Check running services
✔ Access app via NodePort

---

#  CI/CD Flow Summary

## 🔹 Continuous Integration (CI)

* Developer pushes code to GitHub
* Jenkins pulls code
* Maven builds project
* Docker image is created

## 🔹 Continuous Deployment (CD)

* Image pushed to Docker Hub
* Kubernetes pulls image
* Pods are created and scaled

---

#  Workflow Overview

```
Developer → GitHub → Jenkins → Maven Build → Docker Build
→ DockerHub → Kubernetes → Pods Running 
```

---

#  Key Highlights

✔ Automated pipeline
✔ Scalable deployment (2 replicas)
✔ Containerized Java application
✔ End-to-end CI/CD workflow

---
