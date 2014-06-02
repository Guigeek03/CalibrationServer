package fr.utbm.dao.hibernate;

import fr.utbm.dao.AccessPointDao;
import fr.utbm.model.AccessPoint;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class HibernateAccessPointDao extends HibernateDao<AccessPoint> implements AccessPointDao {	

    @Override
    public List<AccessPoint> getAllAccessPoints() {
        return getSession().createCriteria(AccessPoint.class).list();
    }

    @Override
    public AccessPoint getAccessPointByMacAddr(String macAddr) {
         return (AccessPoint) getSession().createCriteria(AccessPoint.class).add(Restrictions.eq(AccessPoint.MAC_ADDR, macAddr)).uniqueResult();
    }

    @Override
    public AccessPoint getAccessPointByID(Integer id) {
         return (AccessPoint) getSession().createCriteria(AccessPoint.class).add(Restrictions.eq(AccessPoint.ID, id)).uniqueResult();
    }

      
    
}