package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static ru.bsc.test.autotester.utils.FileExtensionsUtils.EXTENSION_JSON;
import static ru.bsc.test.autotester.utils.FileExtensionsUtils.extensionByContent;

/**
 * Created by smakarov
 * 23.03.2018 10:57
 */
public abstract class AbstractJsonAttachExtractor extends AbstractAttachExtractor {

    static final String APPLICATION_JSON = "application/json";

    String formatJsonValue(String value) {
        try {
            if (!EXTENSION_JSON.equals(extensionByContent(value))) {
                return value;
            }
            if (value.trim().startsWith("{")) {
                return new JSONObject(value).toString(2);
            } else {
                return new JSONArray(value).toString(2);
            }
        } catch (JSONException e) {
            return value;
        }
    }
}
