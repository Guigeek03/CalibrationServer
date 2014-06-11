package fr.utbm.dao;

import fr.utbm.model.Rssi;
import java.util.List;

/**
 * DAO interface for RSSI model object
 * 
 * @author Guigeek
 */
public interface RssiDao extends Dao<Rssi> {
    /**
     * Retrieves all RSSIs stored in the database
     * @return a list of RSSIs or null
     */
    public List<Rssi> getAllRssis();
    
    /**
     * Retrieves a RSSI by id
     * @param id the RSSI's id
     * @return the corresponding RSSI or null
     */
    public Rssi getRssiByID(Integer id);  
    
    /**
     * Retrieves a RSSI by Access Point and Location given in parameter
     * @param idAP the RSSI's access point
     * @param idLoc the RSSI's location
     * @return the corresponding RSSI or null
     */
    public Rssi getRssiByAPAndLocation(Integer idAP, Integer idLoc);
    
    /**
     * Retrieves the list of RSSIs by Access Point
     * @param idAP the RSSI's access point
     * @return a list of RSSIs or null
     */
    public List<Rssi> getAllRssisByAP(Integer idAP);
}