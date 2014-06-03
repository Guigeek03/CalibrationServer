package fr.utbm.service.impl;

import fr.utbm.dao.AccessPointDao;
import fr.utbm.dao.exception.AccessPointAlreadyExistsException;
import fr.utbm.dao.exception.AccessPointInUseException;
import fr.utbm.dao.exception.AccessPointInexistantException;
import fr.utbm.model.AccessPoint;
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
public class DefaultAccessPointService implements AccessPointService {

    @Resource
    SessionFactory sessionFactory;

    @Resource
    AccessPointDao accessPointDao;

    @Override
    public AccessPoint createAccessPoint(AccessPoint accessPoint) throws AccessPointAlreadyExistsException {
        AccessPoint ap = null;
        try {
            ap = getAccessPointByMacAddr(accessPoint.getMacAddr());
        } catch (AccessPointInexistantException e) {
            accessPointDao.save(accessPoint);
            return accessPoint;
        }

        throw new AccessPointAlreadyExistsException(0, accessPoint.getMacAddr());
    }

    @Override
    public void deleteAccessPointById(Integer id) throws AccessPointInUseException, AccessPointInexistantException {
        AccessPoint ap = accessPointDao.getAccessPointByID(id);
        if (ap != null) {
            deleteAccessPoint(ap);
        } else {
            throw new AccessPointInexistantException(id, "");
        }
    }

    @Override
    public void deleteAccessPoint(AccessPoint accessPoint) throws AccessPointInUseException {
        try {
            accessPointDao.delete(accessPoint);
        } catch (ConstraintViolationException e) {
            throw new AccessPointInUseException(accessPoint.getId(), accessPoint.getMacAddr());
        }
    }

    @Override
    public List<AccessPoint> getAllAccessPoints() {
        List<AccessPoint> list = accessPointDao.getAllAccessPoints();
        if (list == null) {
            return Collections.EMPTY_LIST;
        }

        return list;
    }

    @Override
    public AccessPoint getAccessPointByMacAddr(String macAddr) throws AccessPointInexistantException {
        AccessPoint ap = accessPointDao.getAccessPointByMacAddr(macAddr);
        if (ap == null) {
            throw new AccessPointInexistantException(0, macAddr);
        }
        return ap;
    }

    @Override
    public AccessPoint getAccessPointByID(Integer id) throws AccessPointInexistantException {
        AccessPoint ap = accessPointDao.getAccessPointByID(id);
        if (ap == null) {
            throw new AccessPointInexistantException(id, "");
        }
        return ap;
    }

}
