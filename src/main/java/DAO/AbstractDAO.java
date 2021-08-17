package DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractDAO<T extends Serializable> {
    private Session session;
    private Class<T> clazz;

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public void save(T t) {
        session = openSession();
        session.beginTransaction();
        session.save(t);
        session.getTransaction().commit();
        session.close();
    }

    public void update(T t) {
        session = openSession();
        session.beginTransaction();
        session.update(t);
        session.getTransaction().commit();
        session.close();
    }

    public T findById(int id) {
        return getSessionFactory(clazz).openSession().get(clazz, id);
    }

    public List<T> findAll() {
        session = openSession();
        List<T> list = session.createQuery("from " + clazz.getName()).list();
        session.close();
        return list;
    }

    public int findIndex() {
        session = openSession();
        int index = Integer.parseInt(session.createQuery("select max(id) from " + clazz.getName()).getSingleResult().toString());
        session.close();
        return index;
    }

    public void delete(T t) {
        session = openSession();
        session.beginTransaction();
        session.delete(t);
        session.getTransaction().commit();
        session.close();
    }

    public void deleteAll() {
        List<T> entityList = findAll();
        for (T entity : entityList) {
            delete(entity);
        }
    }

    private SessionFactory getSessionFactory(Class<T> clazz) {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(clazz);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties());
        return configuration.buildSessionFactory(builder.build());
    }

    private Session openSession() {
        return getSessionFactory(clazz).openSession();
    }
}
