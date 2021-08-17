package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyLoader {
    private final static String PATH_TO_PROPERTY = "src\\main\\resources\\application.properties";
    public static String getProperty(String key) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(PATH_TO_PROPERTY));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties.getProperty(key);
    }
}
