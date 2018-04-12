package ru.bsc.test.autotester.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.bsc.test.autotester.diff.Diff;
import ru.bsc.test.autotester.diff.DiffMatchPatch;

import java.util.List;

/**
 * Created by smakarov
 * 05.04.2018 12:19
 */
@Component
@Slf4j
public class JsonDiffCalculator {

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final DiffMatchPatch dmp = new DiffMatchPatch();
    private final JsonParser parser = new JsonParser();

    public List<Diff> calculate(String actual, String expected) {
        String actualJson = actual;
        String expectedJson = expected;

        try {
            JsonElement jsonElement = parser.parse(actual);
            actualJson = gson.toJson(jsonElement);
        } catch (Exception e) {
            log.error("Error while parsing actual result: {}", actual, e);
        }

        try {
            JsonElement jsonElement = parser.parse(expected);
            expectedJson = gson.toJson(jsonElement);
        } catch (Exception e) {
            log.error("Error while parsing expected result: {}", expected, e);
        }

        return dmp.diffMain(expectedJson, actualJson);
    }
}
