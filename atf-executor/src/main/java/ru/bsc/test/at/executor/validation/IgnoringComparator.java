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
        if (IGNORE.equals(expectedValue)) {
            return;
        }

        if (expectedValue instanceof String && actualValue instanceof String && ((String) expectedValue).contains(IGNORE)) {
            if (!MaskComparator.compare((String)expectedValue, (String)actualValue)) {
                result.fail(prefix, expectedValue, actualValue);
            }
        } else {
            super.compareValues(prefix, expectedValue, actualValue, result);
        }
    }
}