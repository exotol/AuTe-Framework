package ru.bsc.test.at.executor.validation;

import java.util.regex.Pattern;

public class MaskComparator {

    private static final String IGNORE = "*ignore*";

    public static boolean compare(Object expectedValue, Object actualValue) {
        String expected = expectedValue.toString();
        String actual = actualValue.toString();
        String[] expectedParts = expected.split(Pattern.quote(IGNORE));
        int position = actual.indexOf(IGNORE);
        for (String value : expectedParts) {
            int valuePos = actual.indexOf(value, position);
            if (valuePos < 0 || valuePos < position) {
                return false;
            }
            position = valuePos + value.length();
        }
        return true;
    }
}
