package fr.utbm.dao;

import java.util.List;
import org.hibernate.Query;
/**
 * Generic DAO interface
 * @author Guigeek
 * @param <T> the model object 
 */
public interface Dao<T> {

    /**
     * Saves the entity into database
     * @param entity the entity to save
     */
    public void save(T entity);
    
    /**
     * Merges the entity into database
     * @param entity the entity to merge
     */
    public void merge(T entity);
    
    /**
     * Deletes the entity from database
     * @param entity the entity to delete
     */
    public void delete(T entity);

    /**
     * Retrieves all objects matching the query
     * @param query the query to perform
     * @return a list of objects matching the query
     */
    public List<T> findMany(Query query);
    
    /**
     * Retrieves an object by id
     * @param clazz the class of the object 
     * @param id the id of the object
     * @return the corresponding object or null
     */
    public T findById(Class clazz, Integer id);

    /**
     * Retrieves all objects of type Class
     * @param clazz the type of objects
     * @return a list of objects
     */
    public List findAll(Class clazz);
}
