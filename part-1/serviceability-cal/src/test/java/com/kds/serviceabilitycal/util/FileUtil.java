package com.kds.serviceabilitycal.util;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Objects;

public class FileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    private FileUtil() {

    }

    public static String getStringFromFile(String fileName) {

        String result = "";
        ClassLoader classLoader = FileUtil.class.getClassLoader();
        try {
            result = IOUtils.toString(Objects.requireNonNull(classLoader.getResourceAsStream(fileName)), "UTF-8");
        } catch (IOException e) {
            LOGGER.error("Error while reading the file in resources", e);
        }
        return result;
    }
}
