package fr.utbm.service;

import fr.utbm.dao.exception.AccessPointAlreadyExistsException;
import fr.utbm.dao.exception.AccessPointInUseException;
import fr.utbm.dao.exception.AccessPointInexistantException;
import fr.utbm.model.AccessPoint;
import java.util.List;

/**
 * Service interface for Access point model object
 * 
 * @author Guigeek
 */
public interface AccessPointService {

    /**
     * Creates a new access point
     * @param accessPoint the new access point
     * @return the newly created access point in the database
     * @throws AccessPointAlreadyExistsException 
     */
    public AccessPoint createAccessPoint(AccessPoint accessPoint) throws AccessPointAlreadyExistsException;

    /**
     * Deletes an access point by id
     * @param id the access point's to delete
     * @throws AccessPointInUseException
     * @throws AccessPointInexistantException 
     */
    public void deleteAccessPointById(Integer id) throws AccessPointInUseException, AccessPointInexistantException;

    /**
     * Deletes an access point
     * @param accessPoint the access point to delete
     * @throws AccessPointInUseException 
     */
    public void deleteAccessPoint(AccessPoint accessPoint) throws AccessPointInUseException;

    /**
     * Retrieves all access points stored in the database
     * @return a list of access points or an empty list
     */
    public List<AccessPoint> getAllAccessPoints();

    /**
     * Retrieves an access point by mac address
     * @param macAddr the access point's mac address
     * @return the corresponding access point
     * @throws AccessPointInexistantException 
     */
    public AccessPoint getAccessPointByMacAddr(String macAddr) throws AccessPointInexistantException;

    /**
     * Retrieves an access point by id
     * @param id the access point's id
     * @return the corresponding access point
     * @throws AccessPointInexistantException 
     */
    public AccessPoint getAccessPointByID(Integer id) throws AccessPointInexistantException;

}
