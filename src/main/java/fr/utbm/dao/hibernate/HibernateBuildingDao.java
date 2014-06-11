package fr.utbm.dao.hibernate;

import fr.utbm.dao.BuildingDao;
import fr.utbm.model.Building;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Hibernate implementation of Building DAO interface
 * 
 * @author Guigeek
 */
@Component
public class HibernateBuildingDao extends HibernateDao<Building> implements BuildingDao {	
    @Override
    public List<Building> getAllBuildings() {
        return getSession().createCriteria(Building.class).list();
    }

    @Override
    public Building getBuildingByName(String name) {
         return (Building) getSession().createCriteria(Building.class).add(Restrictions.eq(Building.NAME, name)).uniqueResult();
    }

    @Override
    public Building getBuildingByID(Integer id) {
        return (Building) getSession().createCriteria(Building.class).add(Restrictions.eq(Building.ID, id)).uniqueResult();
    }
      
    
}