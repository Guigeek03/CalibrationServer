
package fr.utbm.dao.hibernate;

import fr.utbm.dao.LocationDao;
import fr.utbm.model.Location;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class HibernateLocationDao extends HibernateDao<Location> implements LocationDao {

    @Override
    public List<Location> getAllLocations() {
        return getSession().createCriteria(Location.class).list();
    }

    @Override
    public Location getLocationByID(Integer id) {
        return (Location) getSession().createCriteria(Location.class).add(Restrictions.eq(Location.ID, id)).uniqueResult();
    }    
}
