package fr.utbm.dao;

import fr.utbm.model.Location;
import java.util.List;

/**
 * DAO interface for location model object
 * 
 * @author Guigeek
 */
public interface LocationDao extends Dao<Location> {
    
    /**
     * Retrieves all locations stored in the database
     * @return a list of locations or null
     */
    public List<Location> getAllLocations();
    
    /**
     * Retrieves a location by map
     * @param mapId the location's map
     * @return a list of locations or null
     */
    public List<Location> getLocationByMap(Integer mapId);
    
    /**
     * Retrieves a location by id
     * @param id the location's id
     * @return the corresponding location or null 
     */
    public Location getLocationByID(Integer id);  
}