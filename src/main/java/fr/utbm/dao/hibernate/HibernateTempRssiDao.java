
package fr.utbm.dao.hibernate;

import fr.utbm.dao.TempRssiDao;
import fr.utbm.model.TempRssi;
import java.util.List;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class HibernateTempRssiDao extends HibernateDao<TempRssi> implements TempRssiDao {

    @Override
    public List<TempRssi> getAllTempRssis() {
        return getSession().createCriteria(TempRssi.class).list();
    }

    @Override
    public TempRssi getTempRssiByID(Integer id) {
        return (TempRssi) getSession().createCriteria(TempRssi.class).add(Restrictions.eq(TempRssi.ID, id)).uniqueResult();
    }

    @Override
    public TempRssi getTempRssiByAP(Integer idAP) {
        return (TempRssi) getSession().createCriteria(TempRssi.class).add(Restrictions.eq(TempRssi.ACCESS_POINT_ID, idAP)).uniqueResult();
    }
}
