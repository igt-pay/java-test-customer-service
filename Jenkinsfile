pipeline {

    agent {
        kubernetes {
            cloud 'leaas'
            inheritFrom 'mvn39-jdk21'
        }
    }

    triggers {
        githubPush()
    }

    options {
        ansiColor('xterm')
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }

    stages {
        stage('Prepare build environment') {
            steps {
                container('maven') {
                    configFileProvider([configFile(fileId: 'pay-maven-settings', targetLocation: 'settings.xml')]) {
                        sh "mvn clean -s settings.xml -B -U"
                    }
                }
            }
        }

        stage('Build artifact') {
            steps {
                container('maven') {
                    configFileProvider([configFile(fileId: 'pay-maven-settings', targetLocation: 'settings.xml')]) {
                        sh "mvn deploy -s settings.xml -B -U -DskipTests"
                    }
                }
            }
        }

    }

}