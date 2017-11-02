package ru.bsc.test.at.executor.validation;

import java.util.Comparator;

public class MaskComparator implements Comparator {

    private static final String IGNORE = "*ignore*";

    @Override
    public int compare(Object expectedValue, Object actualValue) {
        String expected = expectedValue.toString();
        String actual = actualValue.toString();
        String[] expectedParts = expected.split("\\*ignore\\*");
        int position = actual.indexOf(IGNORE);
        for (String value : expectedParts) {
            int valuePos = actual.indexOf(value, position);
            if (valuePos < 0 || valuePos < position) {
                return -1;
            }
            position = valuePos + value.length();
        }
        return 1;
    }
}
