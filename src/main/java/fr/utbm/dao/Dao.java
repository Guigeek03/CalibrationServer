package fr.utbm.dao;

import java.util.List;
import org.hibernate.Query;

public interface Dao<T> {

    public void save(T entity);

    public void merge(T entity);

    public void delete(T entity);

    public List<T> findMany(Query query);
    
    public T findById(Class clazz, Integer id);

    public List findAll(Class clazz);
}
