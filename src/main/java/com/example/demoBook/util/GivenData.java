package com.example.demoBook.util;

import java.io.IOException;
import java.io.InputStream;

public class GivenData {
    public GivenData() {}

    public static String dataExpect(String path) throws IOException {
        String dataResponse = getData(path);
        return dataResponse;
    }

    public static String getData(String pathData) throws IOException {
        InputStream inputStream = GivenData.class.getClassLoader().getResourceAsStream(pathData);
        String result =inputStream.toString();
        return result;
    }
}
