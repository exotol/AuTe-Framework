package ru.bsc.test.autotester.component.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import ru.bsc.test.autotester.component.Translator;

/**
 * Created by smakarov
 * 01.03.2018 15:11
 */
@Component
public class LimitTransliterationTranslator implements Translator {

    private static final String[] LATIN_ALPHABET_CHARS = new String[]{
            "a", "b", "v", "g", "d", "e", "yo", "g", "z", "i", "y", "i", "k", "l", "m",
            "n", "o", "p", "r", "s", "t", "u", "f", "h", "tz", "ch", "sh", "sh", "", "e", "yu", "ya"};
    private static final String CYRILLIC_ALPHABET = "абвгдеёжзиыйклмнопрстуфхцчшщьэюя";
    private static final int RESULT_MAX_LENGTH = 40;

    @Override
    public String translate(String text) {
        if (StringUtils.isBlank(text)) {
            return "";
        }
        StringBuilder translated = new StringBuilder("");
        for (char ch : text.toLowerCase().toCharArray()) {
            int charIndex = CYRILLIC_ALPHABET.indexOf(ch);
            translated.append(charIndex != -1 ? LATIN_ALPHABET_CHARS[charIndex] : ch);
        }
        String result = translated.toString().replaceAll("[^a-zA-Z0-9.-_]", "-");
        return result.length() > RESULT_MAX_LENGTH ? result.substring(0, RESULT_MAX_LENGTH) : result;
    }
}
