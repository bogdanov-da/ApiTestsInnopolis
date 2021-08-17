package DAO;

import DBO.customers.Customer;

public class CustomerDAO extends AbstractDAO<Customer>{
    public CustomerDAO() {
        setClazz(Customer.class);
    }
}
