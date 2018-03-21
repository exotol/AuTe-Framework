package ru.bsc.test.autotester.utils;

/**
 * Created by smakarov
 * 21.03.2018 14:04
 */
public final class MimeTypeUtils {

    public static final String EXTENSION_JSON = "json";
    public static final String EXTENSION_XML = "xml";
    public static final String EXTENSION_TXT = "txt";

    private MimeTypeUtils() {

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
        return "txt";
    }

    public static String mimeTypeByExtension(String extension) {
        if (extension == null) {
            extension = "";
        }
        switch (extension) {
            case EXTENSION_JSON:
                return "application/json";
            case EXTENSION_XML:
                return "text/xml";
            case EXTENSION_TXT:
            default:
                return "text/plain";
        }
    }
}
