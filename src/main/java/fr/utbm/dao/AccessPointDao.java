package fr.utbm.dao;

import fr.utbm.model.AccessPoint;
import java.util.List;

/**
 * DAO interface for access point model object
 *
 * @author Guigeek
 */
public interface AccessPointDao extends Dao<AccessPoint> {

    /**
     * Retrieves all access points stored in the database
     *
     * @return a list of access points or null
     */
    public List<AccessPoint> getAllAccessPoints();

    /**
     * Retrieves an access point with the mac address given in parameter
     * @param macAddr the access point's mac address
     * @return the corresponding access point or null
     */
    public AccessPoint getAccessPointByMacAddr(String macAddr);

    /**
     * Retrieves an access point by id
     * @param id the access point's id
     * @return the corresponding access point or null
     */
    public AccessPoint getAccessPointByID(Integer id);
}
