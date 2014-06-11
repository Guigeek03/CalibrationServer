package fr.utbm.dao.hibernate;

import fr.utbm.dao.MapDao;
import fr.utbm.model.Map;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate implementation of Map DAO interface
 * 
 * @author Guigeek
 */
@Component
public class HibernateMapDao extends HibernateDao<Map> implements MapDao {	
    @Override
    public List<Map> getAllMaps() {
        return getSession().createCriteria(Map.class).list();
    }
    
    @Override
    public List<Map> getMapsForBuildingID(Integer id) {
        return getSession().createCriteria(Map.class).add(Restrictions.eq("building.id", id)).list();
    }

    @Override
    public Map getMapByID(Integer id) {
        return (Map) getSession().createCriteria(Map.class).add(Restrictions.eq(Map.ID, id)).uniqueResult();
    }
      
    
}