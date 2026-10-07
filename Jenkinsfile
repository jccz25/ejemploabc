pipeline {
    agent any

    tools {
        // Le indica a Jenkins que descargue y use la herramienta "Maven3" configurada en Tools
        maven 'Maven3'
    }

    stages {
        stage('1. Checkout Code') {
            steps {
                echo 'Obteniendo el codigo del repositorio...'
            }
        }

        stage('2. Build & Unit Tests') {
            steps {
                echo 'Ejecutando Pruebas Unitarias con JUnit y Mockito...'
                sh 'mvn clean test'
            }
        }

        stage('3. SonarQube Analysis') {
            steps {
                echo 'Enviando analisis de calidad a SonarQube local...'
                sh 'mvn verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.host.url=http://host.docker.internal:9000 -Dsonar.login=admin -Dsonar.password=password'
            }
        }

        //MArca un error al contruir "docker: not found"
        //stage('4. Docker Build') {
        //    steps {
        //        echo 'Construyendo la imagen Docker de la aplicacion...'
        //        sh 'docker build -t ejemploabc-api:latest .'
        //    }

        stage('4. Docker Build') {
            steps {
                echo 'Construyendo la imagen OCI/Docker de la aplicacion con Jib...'
                //sh 'mvn jib:dockerBuild'
                sh 'mvn clean compile jib:build'
            }
        }



        
    }
}

