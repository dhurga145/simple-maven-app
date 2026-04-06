# 🚀 Jenkins CI Pipeline using Git and Maven (Complete Guide)

---

## 📌 Project Overview

This project demonstrates a **Continuous Integration (CI) Pipeline** using:

* Git (Version Control)
* Maven (Build Tool)
* Jenkins (Automation Server)

The pipeline:
✔ Clones code from GitHub
✔ Builds using Maven
✔ Runs JUnit tests
✔ Reports success/failure

---

## 🛠️ Tech Stack

* Java
* Maven
* Jenkins
* GitHub
* JUnit

---

## 📁 Project Structure (Maven Standard)

```
simple-maven-app/
├── pom.xml
└── src/
    ├── main/java/com/example/App.java
    └── test/java/com/example/AppTest.java
```

---

## ⚙️ STEP 1: Create Maven Project

Project Name:

```
simple-maven-app
```

---

## 💻 STEP 2: App.java (Main Application)

📍 Path:

```
src/main/java/com/example/App.java
```

```java
package com.example;

public class App {
    public int add(int a, int b) {
        return a + b;
    }
}
```

### 🔍 Explanation

* Contains business logic
* `add()` method returns sum

---

## 🧪 STEP 3: AppTest.java (JUnit Test)

📍 Path:

```
src/test/java/com/example/AppTest.java
```

```java
package com.example;

import org.junit.Test;
import static org.junit.Assert.*;

public class AppTest {

    @Test
    public void testAdd() {
        App app = new App();
        assertEquals(5, app.add(2, 3));
    }
}
```

### 🔍 Explanation

* `@Test` marks test method
* `assertEquals` validates output
* Test failure → build failure

---

## 📄 STEP 4: pom.xml

📍 Path:

```
simple-maven-app/pom.xml
```

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://www.w3.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>simple-maven-app</artifactId>
    <version>1.0-SNAPSHOT</version>

    <dependencies>
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

</project>
```

### 🔍 Explanation

* Defines project configuration
* Adds JUnit dependency for testing

---

## 🧪 STEP 5: Test Locally

```bash
mvn clean test
```

### ✅ Expected Output

```
BUILD SUCCESS
```

---

## 📤 STEP 6: Push to GitHub

```bash
git init
git add .
git commit -m "Initial Maven project with JUnit test"
git branch -M main
git remote add origin <your-repository-url>
git push -u origin main
```

---

## ⚙️ STEP 7: Install Maven (Windows)

1. Download: apache-maven-3.9.12-bin.zip
2. Extract to:

```
C:\Program Files\Apache\apache-maven-3.9.12
```

3. Set Environment Variable:

```
MAVEN_HOME = C:\Program Files\Apache\apache-maven-3.9.12
```

4. Add to PATH:

```
%MAVEN_HOME%\bin
```

5. Verify:

```bash
mvn -version
```

---

## ⚙️ STEP 8: Configure Maven in Jenkins

📍 Path:

```
Manage Jenkins → Tools → Maven Installations
```

### Configuration:

* Name: `M3`
* MAVEN_HOME: `C:\Program Files\Apache\apache-maven-3.9.12`
* Install Automatically: ❌ UNCHECK

---

## 🔄 STEP 9: Jenkins Pipeline Script

```groovy
pipeline {
    agent any

    tools {
        maven 'M3'
    }

    stages {

        stage('Checkout Git') {
            steps {
                git branch: 'main',
                url: '<your-repository-url>'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean test'
            }
        }
    }
}
```

---

## ▶️ STEP 10: Run Jenkins Pipeline

### Steps:

1. Save pipeline
2. Click **Build Now**
3. Open **Console Output**

---

## ✅ Expected Output

* Git repository cloned
* Maven build executed
* JUnit tests run
* Output:

```
BUILD SUCCESS
Finished: SUCCESS
```

---

## 📊 CI Pipeline Flow

### 🔹 Continuous Integration (CI)

* Developer pushes code to GitHub
* Jenkins clones repository
* Maven builds project
* JUnit tests are executed

---

## 🔄 Workflow Summary

* Git → Version Control
* Jenkins → Automation
* Maven → Build Tool
* JUnit → Testing

---

## 🎯 Key Features

* Automated build process
* Automatic testing
* Early bug detection
* Clean Maven structure

---

## 🧠 Viva Answer (Short)

👉 “This project demonstrates a CI pipeline where Jenkins automatically fetches code from GitHub, builds it using Maven, runs JUnit tests, and reports build status.”

---

## 👨‍💻 Author

Your Name
