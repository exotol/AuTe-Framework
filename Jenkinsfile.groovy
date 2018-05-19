node() {
	stage('Сборка') {
		echo 'Checkout'
		checkout scm
		echo 'Build project'
		env.JAVA_HOME = tool "ORACLE JDK1.8"
		def mvnHome = tool 'Maven 3.3.9'
		try {
			sh "${mvnHome}/bin/mvn clean package"
		} finally {
			junit 'target/surefire-reports/**/*.xml'
		}
		archiveArtifacts artifacts: 'target/*.jar'
	}
}