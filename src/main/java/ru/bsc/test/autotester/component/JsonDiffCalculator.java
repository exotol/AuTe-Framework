package ru.bsc.test.autotester.component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
        return dmp.diffMain(format(actual), format(expected));
    }

    private String format(String str){

        if(isJson(str)){
            try {
                JsonElement jsonElement = parser.parse(str);
                return gson.toJson(jsonElement);
            } catch (Exception e) {
                return str;
            }
        }

        if (isXml(str)) {
            try {
                Document doc = Jsoup.parse(str);
                return doc.outerHtml();
            } catch (Exception e) {
                return str;
            }
        }
        return str;
    }

    private boolean isJson(String str){
        if(str == null){
            return false;
        }
        String trimmed = str.trim();
        return trimmed.startsWith("{") && trimmed.endsWith("}") ||
               trimmed.startsWith("[") && trimmed.endsWith("]");

    }

    private boolean isXml(String str){
        if(str == null){
            return false;
        }
        String trimmed = str.trim();
        return trimmed.startsWith("<") && trimmed.endsWith(">");

    }
}
