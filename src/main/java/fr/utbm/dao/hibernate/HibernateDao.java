package fr.utbm.dao.hibernate;

import fr.utbm.dao.Dao;
import java.util.List;
import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate implementation of Generic DAO interface
 * 
 * @author Guigeek
 */
public abstract class HibernateDao<T> implements Dao<T> {

    protected Class clazz;

    @Resource
    protected SessionFactory sessionFactory;

    public void setEntityClass(final Class clazz) {
        this.clazz = clazz;
    }

    public DetachedCriteria createDetachedCriteria() {
        return DetachedCriteria.forClass(clazz);
    }

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(T entity) {
        getSession().saveOrUpdate(entity);
    }

    @Override
    public void merge(T entity) {
        getSession().merge(entity);
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
    }

    @Override
    public List<T> findMany(Query query) {
        List<T> list;
        list = (List<T>) query.list();
        return list;
    }

    @Override
    public T findById(Class clazz, Integer id) {
        Query query = getSession().createQuery("from " + clazz.getName() + "where id = " + id);
        return (T) query.uniqueResult();
    }

    @Override
    public List findAll(Class clazz) {
        List T = null;
        Query query = getSession().createQuery("from " + clazz.getName());
        T = query.list();
        return T;
    }
}
