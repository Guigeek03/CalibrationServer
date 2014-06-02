package fr.utbm.service;

import fr.utbm.model.AccessPoint;
import java.util.List;

public interface AccessPointService {

    public AccessPoint createAccessPoint(AccessPoint accessPoint);

    public void updateAccessPoint(Integer id, String newMacAddr);

    public void deleteAccessPointById(Integer id);

    public void deleteAccessPoint(AccessPoint accessPoint);

    public List<AccessPoint> getAllAccessPoints();

    public AccessPoint getAccessPointByMacAddr(String macAddr);

    public AccessPoint getAccessPointByID(Integer id);

}
