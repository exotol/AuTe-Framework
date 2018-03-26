package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import ru.bsc.test.autotester.utils.MimeTypeUtils;

/**
 * Created by smakarov
 * 23.03.2018 10:57
 */
public abstract class AbstractJsonAttachExtractor extends AbstractAttachExtractor {

    String formatJsonValue(String value) {
        try {
            if (!MimeTypeUtils.extensionByContent(value).equals(MimeTypeUtils.EXTENSION_JSON)) {
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
