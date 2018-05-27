node() {
	if (env.BRANCH_NAME=='master'){
		echo 'master branch - disable build discarding'
		properties([buildDiscarder(logRotator(artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: ''))])
	} else {
		properties([[$class: 'BuildDiscarderProperty', strategy: [$class: 'LogRotator', artifactDaysToKeepStr: '', artifactNumToKeepStr: '', daysToKeepStr: '', numToKeepStr: '5']]]);
	}
	stage('Сборка') {
		echo 'Checkout'
		checkout scm
		echo 'Build project'
		env.JAVA_HOME = tool "ORACLE JDK1.8"
		def mvnHome = tool 'Maven 3.3.9'
		try {
			def shortCommit = sh(returnStdout: true, script: "git log -n 1 --pretty=format:'%h'").trim()
			def commitCount = sh(returnStdout: true, script: "git rev-list HEAD --count").trim()
			currentBuild.description = "${BRANCH_NAME}.${commitCount}.${shortCommit}"
			sh "${mvnHome}/bin/mvn clean package -P npm-install"
		} catch (exception) {
			emailext(
					attachLog: true,
					body: 'Сборка ATF нестабильна.\n Проверьте лог сборки ${BUILD_URL}" \n Состав сборки:\n' + getChangeLog(),
					recipientProviders: [[$class: 'CulpritsRecipientProvider'], [$class: 'DevelopersRecipientProvider']],
					subject: "Сборка ATF ${BUILD_NUMBER} нестабильна"
			)
		} finally {
			junit '**/target/surefire-reports/**/*.xml'
		}
	}
	stage('Архивирование артефактов') {
		archiveArtifacts artifacts: '**/target/*.jar,**/target/**/run.bat,**/target/**/env.yml.sample,**/target/**/logback-spring.xml'
	}
	stage('Отправка e-mail уведомлений') {
		def sendToList = 'pavel.golovkin@bsc-ideas.com'
		emailext(
				attachLog: true,
				body: 'Доступна новая сборка ATF ${BUILD_URL}\nСостав сборки:\n' + getChangeLog(),
				to: sendToList,
				subject: "Новая версия ATF ${BUILD_NUMBER}"
		)
	}
}

@NonCPS
String getChangeLog() {
	def changeLogSets = currentBuild.changeSets
	def changelogText = ""
	for (changeSet in changeLogSets) {
		def entries = changeSet.items
		for (entry in entries) {
			changelogText += "${entry.commitId} by ${entry.author} on ${new Date(entry.timestamp)}: ${entry.msg} \n"
		}
	}
	changelogText
}