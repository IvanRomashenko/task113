package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL_KEY = "jdbc:mysql://localhost:3306/pp113jdbs";
    private static final String USERNAME_KEY = "root";
    private static final String PASSWORD_KEY = "root";

    private Util() {
    }

    public static Connection connection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL_KEY, USERNAME_KEY, PASSWORD_KEY);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
