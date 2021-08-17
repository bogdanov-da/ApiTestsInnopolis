package apiTests;

import DBO.customers.Customer;
import utils.DBConnectionFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private static Connection connection = DBConnectionFactory.newConnection();

    public List<Customer> getCustomers() {
        Statement statement = null;
        ResultSet resultSet = null;
        List<Customer> customers = new ArrayList<>();
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM customers");
            while (resultSet.next()) {
                customers.add(new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { statement.close(); } catch(SQLException se) {}
            try { resultSet.close(); } catch(SQLException se) {}
        }
        return customers;
    }

    public Customer getCustomerById(int id) {
        Statement statement = null;
        ResultSet resultSet = null;
        Customer customer = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(String.format("SELECT * FROM customers WHERE id = %s", id));
            while (resultSet.next()) {
                customer = new Customer(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { statement.close(); } catch(SQLException se) {}
            try { resultSet.close(); } catch(SQLException se) {}
        }
        return customer;
    }

    public void createCustomers() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO customers VALUES (0, 'email@email.ee', 'defaultName', 1)");
            statement.executeUpdate("INSERT INTO customers VALUES (0, 'email@email.ee', 'defaultName2', 0)");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { statement.close(); } catch(SQLException se) {}
        }
    }

    public void deleteAllCustomers() {
        Statement statement = null;
        try {
            statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM customers");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try { statement.close(); } catch(SQLException se) {}
        }
    }

    public void closeConnection() {
        if(connection != null) {
            try {
                connection.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }
}
