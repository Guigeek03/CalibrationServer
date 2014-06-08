package fr.utbm.service.impl;

import fr.utbm.dao.RssiDao;
import fr.utbm.dao.exception.RssiAlreadyExistsException;
import fr.utbm.dao.exception.RssiInUseException;
import fr.utbm.dao.exception.RssiInexistantException;
import fr.utbm.model.Rssi;
import fr.utbm.service.*;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultRssiService implements RssiService {

    @Resource
    SessionFactory sessionFactory;

    @Resource
    RssiDao rssiDao;

    @Override
    public Rssi createRssi(Rssi rssi) throws RssiAlreadyExistsException {
        if (rssiDao.getAllRssis().contains(rssi)) {
            throw new RssiAlreadyExistsException(rssi.getId(), rssi.getLocation().getId(), rssi.getAccessPoint().getId());
        }

        rssiDao.save(rssi);
        return rssi;
    }

    @Override
    public void deleteRssiById(Integer id) throws RssiInUseException, RssiInexistantException {
        Rssi rssi = rssiDao.getRssiByID(id);
        if (rssi != null) {
            deleteRssi(rssi);
        } else {
            throw new RssiInexistantException(id, 0, 0);
        }
    }

    @Override
    public void deleteRssi(Rssi rssi) throws RssiInUseException {
        try {
            rssiDao.delete(rssi);
        } catch (ConstraintViolationException e) {
            throw new RssiInUseException(rssi.getId(), rssi.getLocation().getId(), rssi.getAccessPoint().getId());
        }
    }

    @Override
    public List<Rssi> getAllRssis() {
        List<Rssi> list = rssiDao.getAllRssis();
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    @Override
    public Rssi getRssiByID(Integer id) throws RssiInexistantException {
        Rssi rssi = rssiDao.getRssiByID(id);
        if (rssi == null) {
            throw new RssiInexistantException(id, 0, 0);
        }
        return rssi;
    }

    @Override
    public Rssi getRssiByAPAndLocation(Integer idAP, Integer idLoc) throws RssiInexistantException {
        Rssi rssi = rssiDao.getRssiByAPAndLocation(idAP, idLoc);
        if (rssi == null) {
            throw new RssiInexistantException(0, idAP, idLoc);
        }
        return rssi;
    }
    
    @Override
    public List<Rssi> getAllRssisByAP(Integer idAP) {
        List<Rssi> list = rssiDao.getAllRssisByAP(idAP);
        
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }
}
