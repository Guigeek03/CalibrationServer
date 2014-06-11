package fr.utbm.service;

import fr.utbm.dao.exception.MapAlreadyExistsException;
import fr.utbm.dao.exception.MapInUseException;
import fr.utbm.dao.exception.MapInexistantException;
import fr.utbm.model.Map;
import java.util.List;

/**
 * Service interface for Map model object
 *
 * @author Guigeek
 */
public interface MapService {

    /**
     * Creates a new map
     *
     * @param map the new map
     * @return the newly created map
     * @throws MapAlreadyExistsException
     */
    public Map createMap(Map map) throws MapAlreadyExistsException;

    /**
     * Deletes a map by id
     *
     * @param id the map's id to delete
     * @throws MapInUseException
     * @throws MapInexistantException
     */
    public void deleteMapById(Integer id) throws MapInUseException, MapInexistantException;

    /**
     * Deletes a map
     *
     * @param map the map to delete
     * @throws MapInUseException
     */
    public void deleteMap(Map map) throws MapInUseException;

    /**
     * Retrieves all maps stored in the database
     *
     * @return a list of maps or an empty list
     */
    public List<Map> getAllMaps();

    /**
     * Retrieves all maps with building id given in parameter
     *
     * @param id the map's building id
     * @return a list of corresponding maps or an empty list
     */
    public List<Map> getMapsForBuildingID(Integer id);

    /**
     * Retrieves a map by id
     *
     * @param id the map's id
     * @return the corresponding map
     * @throws MapInexistantException
     */
    public Map getMapByID(Integer id) throws MapInexistantException;

}
