package db;

import DBO.customers.Customer;
import client.rest.db.CustomerDBClient;
import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.List;

public class DataBaseTests {
    private static CustomerDBClient dbClient;

    @BeforeAll
    public static void createCustomers() {
        dbClient = new CustomerDBClient();
        dbClient.saveCustomer(new Customer("email888", "name444", 1));
        dbClient.saveCustomer(new Customer("email4444", "name3334", 0));
    }

    @AfterAll
    public static void clearDB() {
        dbClient.deleteAllCustomers();
    }

    @Test
    void getCustomersTest() {
        List<Customer> actualCustomers = dbClient.findAllCustomers();
        Customer[] expectedCustomers = RestAssured.get("http://localhost:3000/customers/").as(Customer[].class);
        Assertions.assertEquals(expectedCustomers[0], actualCustomers.get(0));
        Assertions.assertEquals(expectedCustomers.length, actualCustomers.size());
    }

    @Test
    void getCustomerByIdTest() {
        List<Customer> allCustomers = dbClient.findAllCustomers();
        int id = allCustomers.get(RandomUtils.nextInt(0, allCustomers.size() - 1)).getId();
        Customer actualCustomer = dbClient.findCustomerById(id);
        Customer expectedCustomer = RestAssured.get("http://localhost:3000/customers/" + id).as(Customer.class);
        Assertions.assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void createCustomerTest() {
        Customer expectedCustomer = RestAssured.given().contentType(ContentType.JSON).body(generateCustomerBody()).post("http://localhost:3000/customers/").as(Customer.class);
        Customer actualCustomer = dbClient.findCustomerById(expectedCustomer.getId());
        Assertions.assertEquals(expectedCustomer, actualCustomer);
    }

    @Test
    void editCustomerTest() {
        List<Customer> allCustomers = dbClient.findAllCustomers();
        int id = allCustomers.get(RandomUtils.nextInt(0, allCustomers.size() - 1)).getId();
        Customer customerBeforeRequest = dbClient.findCustomerById(id);
        Customer expectedCustomer = RestAssured.given().contentType(ContentType.JSON).body(generateCustomerBody()).put("http://localhost:3000/customers/" + id).as(Customer.class);
        Customer customerAfterRequest = dbClient.findCustomerById(id);
        Assertions.assertEquals(expectedCustomer.getId(), customerBeforeRequest.getId());
        Assertions.assertEquals(expectedCustomer, customerAfterRequest);
    }

    @Test
    void deleteCustomerTest() {
        List<Customer> allCustomers = dbClient.findAllCustomers();
        int id = allCustomers.get(RandomUtils.nextInt(0, allCustomers.size() - 1)).getId();
        Response expectedCustomer = RestAssured.delete("http://localhost:3000/customers/" + id);
        Customer customerAfterRequest = dbClient.findCustomerById(id);
        Assertions.assertEquals("Customer was deleted successfully!", expectedCustomer.getBody().jsonPath().getString("message"));
        Assertions.assertNull(customerAfterRequest);
    }

    @Test
    void checkUpdateHibernateMethod() {
        Customer testCustomerBefore = new Customer("testEmail", "testName", 0);
        dbClient.saveCustomer(testCustomerBefore);
        List<Customer> allCustomers = dbClient.findAllCustomers();
        Customer savedCustomer = allCustomers.get(allCustomers.size() - 1);
        Customer testCustomerAfter = new Customer( savedCustomer.getId(), "testEmail2", "testName2", 1);
        dbClient.updateCustomer(testCustomerAfter);
        Assertions.assertEquals(testCustomerAfter, dbClient.findCustomerById(savedCustomer.getId()));
    }

    @Test
    void checkUpdateHibernateMethodWithIndex() {
        Customer testCustomerBefore = new Customer("testEmail", "testName", 0);
        dbClient.saveCustomer(testCustomerBefore);
        Customer testCustomerAfter = new Customer(dbClient.findIndexCustomer(), "testEmail2", "testName2", 1);
        dbClient.updateCustomer(testCustomerAfter);
        Assertions.assertEquals(testCustomerAfter, dbClient.findCustomerById(dbClient.findIndexCustomer()));
    }

    @Test
    void checkDeleteHibernateMethod() {
        Customer testCustomerBefore = new Customer("testEmail", "testName", 0);
        dbClient.saveCustomer(testCustomerBefore);
        List<Customer> allCustomers = dbClient.findAllCustomers();
        Customer savedCustomer = allCustomers.get(allCustomers.size() - 1);
        dbClient.deleteCustomer(savedCustomer);
        Assertions.assertNull(dbClient.findCustomerById(savedCustomer.getId()));
    }

    private String generateCustomerBody() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", RandomStringUtils.randomAlphanumeric(4) + "@test.tt");
        jsonObject.addProperty("name", RandomStringUtils.randomAlphabetic(5));
        jsonObject.addProperty("active", RandomUtils.nextInt(0, 1));
        return jsonObject.toString();
    }
}
