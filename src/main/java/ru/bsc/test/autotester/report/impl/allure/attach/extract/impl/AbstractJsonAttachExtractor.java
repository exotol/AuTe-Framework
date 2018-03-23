package ru.bsc.test.autotester.report.impl.allure.attach.extract.impl;

import org.json.JSONArray;
import org.json.JSONObject;
import ru.bsc.test.autotester.utils.MimeTypeUtils;

/**
 * Created by smakarov
 * 23.03.2018 10:57
 */
public abstract class AbstractJsonAttachExtractor extends AbstractAttachExtractor {

    String formatJsonValue(String value) {
        if (MimeTypeUtils.extensionByContent(value).equals(MimeTypeUtils.EXTENSION_JSON)) {
            if (value.startsWith("{")) {
                return new JSONObject(value).toString(2);
            } else {
                return new JSONArray(value).toString(2);
            }
        }
        return value;
    }
}
