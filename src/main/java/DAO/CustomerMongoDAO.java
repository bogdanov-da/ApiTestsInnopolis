package DAO;

import DBO.customers.CustomerMongo;
import utils.PropertyLoader;

import static utils.MongoDBConnectionFactory.createMongoClient;

public class CustomerMongoDAO extends AbstractMongoDAO<CustomerMongo>{
    public CustomerMongoDAO() {
        setClient(createMongoClient(PropertyLoader.getProperty("mongodbUrl")));
        setClazz(CustomerMongo.class);
        setCollectionName(PropertyLoader.getProperty("collection"));
        setDbName(PropertyLoader.getProperty("mongodb"));
        setMapper();
    }
}
