package client.rest.db;

import DAO.CustomerDAO;
import DBO.customers.Customer;

import java.util.List;

public class CustomerDBClient {
    private CustomerDAO customerDAO;

    public CustomerDBClient() {
        this.customerDAO = new CustomerDAO();
    }

    public void saveCustomer(Customer customer) {
        customerDAO.save(customer);
    }

    public Customer findCustomerById(int id) {
        return customerDAO.findById(id);
    }

    public List<Customer> findAllCustomers() {
        return customerDAO.findAll();
    }

    public void updateCustomer(Customer customer) {
        customerDAO.update(customer);
    }

    public int findIndexCustomer() {
        return customerDAO.findIndex();
    }

    public void deleteCustomer(Customer customer) {
        customerDAO.delete(customer);
    }

    public void deleteAllCustomers() {
        customerDAO.deleteAll();
    }
}
