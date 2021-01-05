package com.olga.grts.statistic.utils;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

public class Utils {

    private static final String PATH_TO_FILES = "/src/main/resources/static/";

    public static Path generateFilePath() {
        String basePath = new File("").getAbsolutePath();
        return Paths.get(basePath+ PATH_TO_FILES + LocalDateTime.now() +".txt");
    }
}
