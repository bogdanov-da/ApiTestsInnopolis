package client.rest.db;

import DAO.CustomerMongoDAO;
import DBO.customers.CustomerMongo;
import com.mongodb.client.MongoClient;
import org.bson.conversions.Bson;

import java.util.List;

public class CustomerMongoDBClient {
    private CustomerMongoDAO customerMongoDAO;

    public CustomerMongoDBClient() {
        this.customerMongoDAO = new CustomerMongoDAO();
    }

    public CustomerMongo findCustomerById(String id) {
        return customerMongoDAO.findById(id);
    }

    public CustomerMongo findCustomerByFilter(Bson filter) {
        return customerMongoDAO.findByFilter(filter);
    }

    public List<CustomerMongo> findAllCustomers() {
        return customerMongoDAO.findAll();
    }

    public void createCustomer(CustomerMongo customer) {
        customerMongoDAO.create(customer);
    }

    public void deleteCustomer(CustomerMongo customer) {
        customerMongoDAO.delete(customer);
    }

    public void createCustomers(List<CustomerMongo> customers) {
        customerMongoDAO.create(customers);
    }

    public void updateCustomer(Bson filter, CustomerMongo customer) {
        customerMongoDAO.update(filter, customer);
    }

    public void deleteAllCustomers() {
        customerMongoDAO.delete();
    }

    public void closeConnection() {
        MongoClient client = customerMongoDAO.getClient();
        if(client!=null) client.close();
    }
}
