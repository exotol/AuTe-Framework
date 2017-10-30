package ru.bsc.test.at.executor.validation;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.JSONCompareResult;
import org.skyscreamer.jsonassert.comparator.DefaultComparator;

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
                String[] expectedParts = expected.split("\\*ignore\\*");
                if (!containsValues(expectedParts, actual)) {
                    result.fail(prefix, expectedValue, actualValue);
                }
            }
            else {
                super.compareValues(prefix, expectedValue, actualValue, result);
            }
        }
    }

    private boolean containsValues(String[] values, String actual) {
        boolean result = true;
        for (String value : values) {
            if (actual.contains(value)) {
                StringUtils.replaceOnce(actual, value, "");
            }
            else {
                result = false;
            }
        }
        return result;
    }
}
