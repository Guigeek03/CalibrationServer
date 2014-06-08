
package fr.utbm.dao.hibernate;

import fr.utbm.dao.RssiDao;
import fr.utbm.model.Rssi;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class HibernateRssiDao extends HibernateDao<Rssi> implements RssiDao {

    @Override
    public List<Rssi> getAllRssis() {
        return getSession().createCriteria(Rssi.class).list();
    }

    @Override
    public Rssi getRssiByID(Integer id) {
        return (Rssi) getSession().createCriteria(Rssi.class).add(Restrictions.eq(Rssi.ID, id)).uniqueResult();
    }

    @Override
    public Rssi getRssiByAPAndLocation(Integer idAP, Integer idLoc) {
        return (Rssi) getSession().createCriteria(Rssi.class).add(Restrictions.and(Restrictions.eq("accessPoint.id", idAP), Restrictions.eq("location.id", idLoc))).uniqueResult();
    }
        
    @Override
    public List<Rssi> getAllRssisByAP(Integer idAP) {
        return getSession().createCriteria(Rssi.class).add(Restrictions.eq("accessPoint.id", idAP)).list();
    }
}
