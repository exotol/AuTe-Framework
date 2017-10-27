package ru.bsc.test.at.executor.validation;

import org.json.JSONException;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;

/**
 * Created by rrudakov on 10/7/16.
 * Project name bcs-rest-at
 */
public class IgnoringComparator extends DefaultComparator {

    private static final String IGNORE = "*ignore*";

    public IgnoringComparator(JSONCompareMode mode) {
        super(mode);
    }

    @Override
    public void compareValues(String prefix, Object expectedValue, Object actualValue, JSONCompareResult result) throws JSONException {
        String expected = expectedValue.toString();
        String actual = actualValue.toString();
        if (expected.contains(IGNORE))
            expected = replaceString(expected, actual);
            super.compareValues(prefix, expected, actual, result);
    }

    private String replaceString(String expected, String actual) {
        StringBuilder expectedSb = new StringBuilder(expected);
        StringBuilder actualSb = new StringBuilder(actual);
        int start = expectedSb.indexOf(IGNORE);
        int end = start + IGNORE.length();
        String replace = actualSb.substring(start, actualSb.indexOf("<", start));
        String result = expectedSb.replace(start, end, replace).toString();
        if (!result.contains(IGNORE)) {
            return result;
        }
        return replaceString(result, actualSb.toString());
    }
}
