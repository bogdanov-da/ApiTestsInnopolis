package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionFactory {
    private static Connection connection;

    public DBConnectionFactory() {
    }

    public static Connection newConnection() {
        if(connection == null) {
            try {
                connection = DriverManager.getConnection(PropertyLoader.getProperty("url"),
                        PropertyLoader.getProperty("user"),
                        PropertyLoader.getProperty("password"));
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
        return connection;
    }
}
