import DBO.customers.CustomerMongo;
import client.rest.db.CustomerMongoDBClient;
import com.mongodb.client.model.Filters;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class MongoDBTests {
    private static CustomerMongoDBClient client;

    @BeforeAll
    public static void setUpDbConnection() {
        client = new CustomerMongoDBClient();
    }

    @AfterAll
    public static void disconnect() {
        client.deleteAllCustomers();
        client.closeConnection();
    }

    @Test
    void getAllCustomersTest() {
        List<CustomerMongo> customersForCreating = Arrays.asList(new CustomerMongo(), new CustomerMongo());
        client.createCustomers(customersForCreating);
        List<CustomerMongo> customersAfterCreating = client.findAllCustomers();
        Assertions.assertArrayEquals(customersForCreating.toArray(), customersAfterCreating.toArray());
    }

    @Test
    void addNewCustomerTest() {
        CustomerMongo customerDB = new CustomerMongo();
        client.createCustomer(customerDB);
        CustomerMongo customer = client.findCustomerByFilter(Filters.eq("name", customerDB.getName()));
        Assertions.assertEquals(customerDB.getName(), customer.getName());
    }

    @Test
    void findCustomerByIdTest() {
        CustomerMongo customer = new CustomerMongo();
        client.createCustomer(customer);
        CustomerMongo customerAfterCreate = client.findCustomerById(customer.getId().getOid());
        Assertions.assertEquals(customerAfterCreate, customer);
    }

    @Test
    void updateCustomerTest() {
        CustomerMongo customerForCreating = new CustomerMongo();
        CustomerMongo customerForUpdating = new CustomerMongo();
        client.createCustomer(customerForCreating);
        client.updateCustomer(Filters.eq("_id", customerForCreating.getId().getOid()), customerForUpdating);
        Assertions.assertEquals(client.findCustomerById(customerForCreating.getId().getOid()), customerForUpdating);
    }

    @Test
    void deleteCustomerTest() {
        CustomerMongo customerForCreating = new CustomerMongo();
        client.createCustomer(customerForCreating);
        client.deleteCustomer(customerForCreating);
        Assertions.assertNull(client.findCustomerById(customerForCreating.getId().getOid()));
    }
}
