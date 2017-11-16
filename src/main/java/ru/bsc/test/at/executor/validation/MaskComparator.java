package ru.bsc.test.at.executor.validation;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class MaskComparator {

    private static final String IGNORE = "*ignore*";

    public static boolean compare(Object expectedValue, Object actualValue) {
        String expected = expectedValue.toString();
        String actual = actualValue.toString();
        List<String> expectedParts = Arrays.asList(expected.split(Pattern.quote(IGNORE)));
        int position = 0;
        Iterator iterator = expectedParts.iterator();
        while (iterator.hasNext()) {
            String value = (String) iterator.next();
            int valuePos = actual.indexOf(value, position);
            if (valuePos < 0 || valuePos < position) {
                return false;
            }
            if (!expected.endsWith(IGNORE) && !iterator.hasNext()) {
                return actual.substring(valuePos, actual.length()).equals(value);
            }
            position = valuePos + value.length();
        }
        return true;
    }
}
