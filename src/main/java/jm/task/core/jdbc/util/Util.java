package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Util {
    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private Util() {
    }

    private static void loadProperties() {
        try (InputStream is =
                     Util.class.getClassLoader().getResourceAsStream("application.properties")) {
            PROPERTIES.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static String getProperty(String key) {
        return PROPERTIES.getProperty(key);
    }
}
