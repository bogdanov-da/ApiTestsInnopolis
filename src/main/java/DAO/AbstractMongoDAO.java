package DAO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractMongoDAO<T extends Serializable> {
    private MongoClient client;
    private Class<T> clazz;
    private ObjectMapper mapper;
    private String dbName;
    private String collectionName;

    public void setClient(MongoClient client) {
        this.client = client;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void setMapper() {
        this.mapper = new ObjectMapper();
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public MongoClient getClient() {
        return client;
    }

    public List<T> findAll() {
        List<T> allDocuments = new ArrayList<>();
        FindIterable<Document> findIterable = getMongoCollection().find(new BasicDBObject());
        Iterator<Document> iterator = findIterable.iterator();
        while (iterator.hasNext()) {
            try {
                allDocuments.add(mapper.readValue(iterator.next().toJson(), clazz));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        return allDocuments;
    }

    public T findById(String id) {
        T t = null;
        try {
            t = mapper.readValue(getMongoCollection()
                    .find(Filters.eq("_id", new ObjectId(id)))
                    .first().toJson(), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    public T findByFilter(Bson filter) {
        T t = null;
        try {
            t = mapper.readValue(getMongoCollection()
                    .find(filter)
                    .first().toJson(), clazz);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return t;
    }

    public void create(T t) {
        try {
            getMongoCollection().insertOne(Document.parse(mapper.writeValueAsString(t)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void create(List<T> tList) {
        Iterator<T> iterator = tList.iterator();
        while (iterator.hasNext()) {
            try {
                getMongoCollection().insertOne(Document.parse(mapper.writeValueAsString(iterator.next())));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(Bson filter, T t) {
        try {
            getMongoCollection().updateOne(filter, new Document("$set", Document.parse(mapper.writeValueAsString(t))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void delete(T t) {
        try {
            getMongoCollection().deleteOne(Document.parse(mapper.writeValueAsString(t)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        getMongoCollection().deleteMany(new BasicDBObject());
    }

    private MongoCollection<Document> getMongoCollection() {
        return client.getDatabase(dbName).getCollection(collectionName);
    }
}
