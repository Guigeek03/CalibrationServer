package fr.utbm.dao;

import fr.utbm.model.Building;
import java.util.List;

/**
 * DAO interface for building model object
 * @author Guigeek
 */
public interface BuildingDao extends Dao<Building> {
    
    /**
     * Retrieves all buildings stored in the database
     * @return a list of buildings or null
     */
    public List<Building> getAllBuildings();

    /**
     * Retrieves a building by name
     * @param name the building's name
     * @return the corresponding building or null
     */
    public Building getBuildingByName(String name);
    
    /**
     * Retrieves a building by id
     * @param id the building's id
     * @return the corresponding building or null
     */
    public Building getBuildingByID(Integer id);  
}