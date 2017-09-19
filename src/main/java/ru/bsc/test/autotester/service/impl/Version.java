package ru.bsc.test.autotester.service.impl;

/**
 * @author Pavel Golovkin
 */
public class Version {

	public static final String UNKNOWN = "UNKNOWN";

	private String implementationVersion;
	private String implemetationDate;

	public Version(String implementationVersion, String implemetationDate) {
		this.implementationVersion = implementationVersion;
		this.implemetationDate = implemetationDate;
	}

	public String getImplementationVersion() {
		return implementationVersion;
	}

	public void setImplementationVersion(String implementationVersion) {
		this.implementationVersion = implementationVersion;
	}

	public String getImplemetationDate() {
		return implemetationDate;
	}

	public void setImplemetationDate(String implemetationDate) {
		this.implemetationDate = implemetationDate;
	}
}
