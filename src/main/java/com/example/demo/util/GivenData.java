package com.example.demo.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.IOUtils;

public class GivenData {
    public GivenData() {}

    public static String dataExpect(String path) throws IOException {
        return getData(path);
    }

    public static String getData(String pathData) throws IOException {
        InputStream inputStream = GivenData.class.getClassLoader().getResourceAsStream(pathData);
        assert inputStream != null;
        return  IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }
}
