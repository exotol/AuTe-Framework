package ru.bsc.test.autotester.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by sdoroshin on 03.11.2017.
 *
 */

public class YamlUtils {

    public static void dumpToFile(Object data, String fileName) throws IOException {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setAnchorGenerator(new AutotesterAnchorGenerator());

        try(FileWriter fileWriter = new FileWriter(fileName)) {
            new Yaml(new SkipEmptyRepresenter(), dumperOptions)
                    .dump(data, fileWriter);
        }
    }

    public static <T> T loadAs(File fileName, Class<T> type) throws FileNotFoundException {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        return new Yaml(representer).loadAs(new FileReader(fileName), type);
    }
}
