package ru.bsc.servlet.data;

/**
 * Created by smakarov
 * 26.02.2018 15:26
 */
@SuppressWarnings("unused")
public class Version {
    private final String implementationVersion;
    private final String implementationDate;

    public static Version unknown() {
        return new Version("UNKNOWN", "UNKNOWN");
    }

    public Version(String implementationVersion, String implementationDate) {
        this.implementationVersion = implementationVersion;
        this.implementationDate = implementationDate;
    }

    public String getImplementationVersion() {
        return implementationVersion;
    }

    public String getImplementationDate() {
        return implementationDate;
    }

    @Override
    public String toString() {
        return String.format(
                "Version{implementationVersion='%s', implementationDate='%s'}",
                implementationVersion,
                implementationDate
        );
    }
}
