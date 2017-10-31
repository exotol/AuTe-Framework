package ru.bsc.test.autotester.service.impl;

/**
 * @author Pavel Golovkin
 */
public class Version {

	static final String UNKNOWN = "UNKNOWN";

	private String implementationVersion;
	private String implementationDate;

	public Version(String implementationVersion, String implementationDate) {
		this.implementationVersion = implementationVersion;
		this.implementationDate = implementationDate;
	}

	public String getImplementationVersion() {
		return implementationVersion;
	}

	public void setImplementationVersion(String implementationVersion) {
		this.implementationVersion = implementationVersion;
	}

	public String getImplementationDate() {
		return implementationDate;
	}

	public void setImplementationDate(String implementationDate) {
		this.implementationDate = implementationDate;
	}
}
