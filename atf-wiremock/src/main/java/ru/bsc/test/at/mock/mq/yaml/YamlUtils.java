package ru.bsc.test.at.mock.mq.yaml;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.representer.Representer;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by sdoroshin on 03.11.2017.
 */
public final class YamlUtils {
    private YamlUtils() { }

    public static void dumpToFile(Object data, String fileName) throws IOException {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setAnchorGenerator(new AutotesterAnchorGenerator());
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        File file = new File(fileName);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
        }
        try (FileWriter fileWriter = new FileWriter(file)) {
            new Yaml(new SkipEmptyRepresenter(), dumperOptions).dump(data, fileWriter);
        }
    }

    public static <T> T loadAsFromString(String yamlContent, Class<T> type) {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        return new Yaml(representer)
                .loadAs(yamlContent, type);
    }

    public static <T> T loadAs(File fileName, Class<T> type) throws IOException {
        Representer representer = new Representer();
        representer.getPropertyUtils().setSkipMissingProperties(true);
        try (FileReader fileReader = new FileReader(fileName)) {
            return new Yaml(representer)
                    .loadAs(fileReader, type);
        }
    }
}
