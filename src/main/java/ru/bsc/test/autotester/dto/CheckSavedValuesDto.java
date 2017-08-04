package ru.bsc.test.autotester.dto;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by sdoroshin on 02.08.2017.
 *
 */
public class CheckSavedValuesDto {
    private Long stepId;
    private List<KeyValuePair> map;

    public Long getStepId() {
        return stepId;
    }

    public void setStepId(Long stepId) {
        this.stepId = stepId;
    }

    public List<KeyValuePair> getMap() {
        if (map == null) {
            map = new LinkedList<>();
        }
        return map;
    }

    public void setMap(List<KeyValuePair> map) {
        this.map = map;
    }

    public static class KeyValuePair {
        private String key;
        private String value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
