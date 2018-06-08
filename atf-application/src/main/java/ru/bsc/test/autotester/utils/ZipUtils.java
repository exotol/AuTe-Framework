package ru.bsc.test.autotester.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class ZipUtils {

    public static void pack(File sourceDirectory, ZipOutputStream zipOutputStream) throws IOException {
        Path pp = sourceDirectory.toPath();
        try (Stream<Path> paths = Files.walk(pp)) {
          paths
              .filter(path -> !Files.isDirectory(path))
              .forEach(path -> {
                ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                try {
                  zipOutputStream.putNextEntry(zipEntry);
                  Files.copy(path, zipOutputStream);
                  zipOutputStream.closeEntry();
                } catch (IOException e) {
                  log.error("Error while copying zipEntry", e);
                }
              });
        }
    }
}