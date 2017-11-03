package ru.bsc.test.autotester.yaml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by sdoroshin on 03.11.2017.
 *
 */

public class YamlUtils {

    private final static Logger LOGGER = LoggerFactory.getLogger(YamlUtils.class);

    public static void dumpToFile(Object data, String fileName) throws IOException {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setAnchorGenerator(new AutotesterAnchorGenerator());

        File file = new File(fileName);
        if (!file.exists()) {
            if (!file.getParentFile().mkdirs()) {
                LOGGER.info("Directory {} not created", file);
            }
        }
        try(FileWriter fileWriter = new FileWriter(file)) {
            new Yaml(new SkipEmptyRepresenter(), dumperOptions)
                    .dump(data, fileWriter);
        }
    }

    public static <T> T loadAs(File fileName, Class<T> type) throws IOException {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        try(FileReader fileReader = new FileReader(fileName)) {
            return new Yaml(representer)
                    .loadAs(fileReader, type);
        }
    }
}
