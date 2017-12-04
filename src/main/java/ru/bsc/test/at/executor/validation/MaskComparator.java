package ru.bsc.test.at.executor.validation;

import java.util.regex.Pattern;

public class MaskComparator {

    private static final String IGNORE = "*ignore*";

    public static boolean compare(String expected, String actual) {
        expected = expected
                .replaceAll("[\n\r]+", "\n")
                .replaceAll(" ", " ")
                .trim();
        actual = actual
                .replaceAll("[\n\r]+", "\n")
                .replaceAll(" ", " ")
                .trim();
        String[] expectedParts = expected.split(Pattern.quote(IGNORE));
        int position = 0;
        String lastValue = null;
        for (String value : expectedParts) {
            int valuePos = actual.indexOf(value, position);
            if (valuePos < 0 || valuePos < position) {
                return false;
            }
            position = valuePos + value.length();
            lastValue = value;
        }
        return expected.endsWith(IGNORE) || lastValue == null || actual.endsWith(lastValue);
    }
}
