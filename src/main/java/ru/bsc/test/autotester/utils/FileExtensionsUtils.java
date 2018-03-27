package ru.bsc.test.autotester.utils;

/**
 * Created by smakarov
 * 21.03.2018 14:04
 */
public final class FileExtensionsUtils {

    public static final String EXTENSION_JSON = "json";
    public static final String EXTENSION_XML = "xml";
    public static final String EXTENSION_TXT = "txt";

    private FileExtensionsUtils() {

    }

    public static String extensionByContent(String content) {
        if (content != null) {
            String trimmedContent = content.trim();
            if (trimmedContent.startsWith("<")) {
                return EXTENSION_XML;
            }
            if (trimmedContent.startsWith("{") || trimmedContent.startsWith("[")) {
                return EXTENSION_JSON;
            }
        }
        return EXTENSION_TXT;
    }
}
