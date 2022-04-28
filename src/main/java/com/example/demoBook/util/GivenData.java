package com.example.demoBook.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class GivenData {
    public GivenData() {}

    public static String dataExpect(String path) throws IOException {
        String dataResponse = getData(path);
        return dataResponse;
    }

    public static String getData(String pathData) throws IOException {
        InputStream inputStream = GivenData.class.getClassLoader().getResourceAsStream(pathData);
        String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
        return result;
    }
}
