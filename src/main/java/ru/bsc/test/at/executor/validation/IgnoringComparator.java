package ru.bsc.test.at.executor.validation;

import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by rrudakov on 10/7/16.
 * Project name bcs-rest-at
 */
public class IgnoringComparator extends DefaultComparator {

    public IgnoringComparator(JSONCompareMode mode) {
        super(mode);
    }

    @Override
    public void compareValues(String prefix, Object expectedValue, Object actualValue, JSONCompareResult result) throws JSONException {
        if (!expectedValue.equals("*ignore*")) {
            if (expectedValue instanceof JSONObject && actualValue instanceof JSONObject ||
                    expectedValue instanceof String && actualValue instanceof String) {
                String expected = expectedValue.toString();
                String actual = actualValue.toString();
                if (!matchesPattern(expected, actual)) {
                    result.fail(prefix, expectedValue, actualValue);
                }
            }
            else {
                super.compareValues(prefix, expectedValue, actualValue, result);
            }
        }
    }

    private boolean matchesPattern(String expected, String actual) {
        StringBuilder regex = new StringBuilder();
        Boolean isIgnoreEnd = expected.endsWith("*ignore*");
        if (expected.indexOf("*ignore*") == 0) {
            regex.append(".*");
        }
        List<String> exParts = Arrays.asList(expected.split("\\*ignore\\*"));
        for (String s : exParts) {
            regex.append(Pattern.quote(s));
            if (exParts.indexOf(s) == (exParts.size() - 1)) {
                if (isIgnoreEnd) {
                    regex.append(".*");
                }
                else {
                    break;
                }
            }
            regex.append(".*");
        }
        Pattern pattern = Pattern.compile(regex.toString());
        Matcher matcher = pattern.matcher(actual);
        return matcher.matches();
    }
}
