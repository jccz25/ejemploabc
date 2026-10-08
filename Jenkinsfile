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
        stage('4. Docker Build') {
            steps {
                echo 'Construyendo la imagen OCI/Docker de la aplicacion con Jib...'
                //sh 'docker build -t ejemploabc-api:latest .'
                //sh 'mvn jib:dockerBuild'
                //sh 'mvn clean compile jib:build'
                sh 'mvn jib:buildTar'
            }
        }

        // --- FASE 5: DESPLIEGUE EN KUBERNETES ---

        stage('5. Deploy to DEV') {
            steps {
                echo 'Desplegando en el ambiente de DESARROLLO (dev)...'
                //sh 'kubectl apply -f k8s/ -n dev'
                sh 'kubectl apply -f k8s/ -n dev --insecure-skip-tls-verify=true'
            }
        }

        stage('6. Deploy to QA') {
            steps {
                echo 'Desplegando en el ambiente de QA (qa)...'
                //sh 'kubectl apply -f k8s/ -n qa'
                sh 'kubectl apply -f k8s/ -n qa --insecure-skip-tls-verify=true'
            }
        }

        stage('7. Approval for PROD') {
            steps {
                echo 'Esperando aprobacion manual para Produccion...'
                input message: '¿Aprobar despliegue a PRODUCCIÓN?', ok: 'Desplegar'
            }
        }

        stage('8. Deploy to PROD') {
            steps {
                echo 'Desplegando en el ambiente de PRODUCCIÓN (prod)...'
                //sh 'kubectl apply -f k8s/ -n prod'
                sh 'kubectl apply -f k8s/ -n prod --insecure-skip-tls-verify=true'
            }
        }


        
    }
}

