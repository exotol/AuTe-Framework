package ru.bsc.test.at.executor.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

/**
 * Created by sdoroshin on 22.05.2017.
 */
@AllArgsConstructor
@Getter
public class ResponseHelper {
    private final int statusCode;
    private final String content;
    private final Map<String, List<String>> headers;
}
