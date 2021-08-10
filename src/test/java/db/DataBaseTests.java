package db;

import DBO.sakila.Actor;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class DataBaseTests {

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    private String url = "jdbc:mysql://127.0.0.1:3306/sakila";
    private String user = "root";
    private String password = "admin";

    @Test
    void name() {
       try {
           connection = DriverManager.getConnection(url, user, password);
           statement = connection.createStatement();
           resultSet = statement.executeQuery("SELECT * FROM actor");
           while (resultSet.next()) {
              // System.out.println(resultSet.getString(2));
               Actor actor = new Actor(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getDate(4));
               System.out.println(actor);
           }
       } catch (SQLException throwables) {
           throwables.printStackTrace();
       } finally {
           try { connection.close(); } catch(SQLException se) {}
           try { statement.close(); } catch(SQLException se) {}
           try { resultSet.close(); } catch(SQLException se) {}
       }
    }
}
