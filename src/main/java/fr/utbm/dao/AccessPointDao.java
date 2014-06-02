package fr.utbm.dao;

import fr.utbm.model.AccessPoint;
import java.util.List;

public interface AccessPointDao extends Dao<AccessPoint> {
    public List<AccessPoint> getAllAccessPoints();

    public AccessPoint getAccessPointByMacAddr(String macAddr);
    
    public AccessPoint getAccessPointByID(Integer id);  
}