package fr.utbm.dao;

import fr.utbm.model.Map;
import java.util.List;

/**
 * DAO interface for map model object
 *
 * @author Guigeek
 */
public interface MapDao extends Dao<Map> {

    /**
     * Retrieves all maps stored in the database
     * @return a list of maps or null
     */
    public List<Map> getAllMaps();

    /**
     * Retrieves all maps with the building id given in parameter
     * @param id the map's building
     * @return a list of maps with the given id or null
     */
    public List<Map> getMapsForBuildingID(Integer id);

    /**
     * Retrieves a map with the corresponding id
     * @param id the map's id
     * @return the corresponding map or null
     */
    public Map getMapByID(Integer id);
}
