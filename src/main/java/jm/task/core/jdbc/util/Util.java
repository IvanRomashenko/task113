package jm.task.core.jdbc.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final Properties PROPERTIES = new Properties();
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";


    static {
        loadDriver();
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

    public static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection connection() {
        try {
            return DriverManager.getConnection(
                    Util.getProperty(URL_KEY),
                    Util.getProperty(USERNAME_KEY),
                    Util.getProperty(PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
