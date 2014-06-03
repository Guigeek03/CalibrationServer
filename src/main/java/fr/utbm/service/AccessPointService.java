package fr.utbm.service;

import fr.utbm.dao.exception.AccessPointAlreadyExistsException;
import fr.utbm.dao.exception.AccessPointInUseException;
import fr.utbm.dao.exception.AccessPointInexistantException;
import fr.utbm.model.AccessPoint;
import java.util.List;

public interface AccessPointService {

    public AccessPoint createAccessPoint(AccessPoint accessPoint) throws AccessPointAlreadyExistsException;

    public void deleteAccessPointById(Integer id) throws AccessPointInUseException, AccessPointInexistantException;

    public void deleteAccessPoint(AccessPoint accessPoint) throws AccessPointInUseException;

    public List<AccessPoint> getAllAccessPoints();

    public AccessPoint getAccessPointByMacAddr(String macAddr) throws AccessPointInexistantException;

    public AccessPoint getAccessPointByID(Integer id) throws AccessPointInexistantException;

}
