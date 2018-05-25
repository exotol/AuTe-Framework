node() {
	stage('Сборка') {
		echo 'Checkout'
		checkout scm
		echo 'Build project'
		env.JAVA_HOME = tool "ORACLE JDK1.8"
		def mvnHome = tool 'Maven 3.3.9'
		try {
			sh "${mvnHome}/bin/mvn clean package -P npm-install"
		} finally {
			junit '**/target/surefire-reports/**/*.xml'
		}
	}
	stage('Архивирование артефактов') {
		archiveArtifacts artifacts: '**/target/*.jar'
	}
}