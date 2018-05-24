package ru.bsc.test.at.executor.validation;

import java.util.regex.Pattern;

public final class MaskComparator {

    private static final String IGNORE = "*ignore*";

    private MaskComparator() { }

    public static boolean compare(String expected, String actual) {
        String preparedExpected = expected
                .replaceAll("[\n\r]+", "\n")
                .replaceAll(" ", " ")
                .trim();
        String preparedActual = actual
                .replaceAll("[\n\r]+", "\n")
                .replaceAll(" ", " ")
                .trim();
        String[] expectedParts = preparedExpected.split(Pattern.quote(IGNORE));
        int position = 0;
        String lastValue = null;
        for (String value : expectedParts) {
            int valuePos = preparedActual.indexOf(value, position);
            if (valuePos < 0 || valuePos < position) {
                return false;
            }
            position = valuePos + value.length();
            lastValue = value;
        }
        return preparedExpected.endsWith(IGNORE) || lastValue == null || preparedActual.endsWith(lastValue);
    }
}
