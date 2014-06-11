package fr.utbm.service;

import fr.utbm.dao.exception.BuildingAlreadyExistsException;
import fr.utbm.dao.exception.BuildingInUseException;
import fr.utbm.dao.exception.BuildingInexistantException;
import fr.utbm.model.Building;
import java.util.List;

/**
 * Service interface for Building model object
 *
 * @author Guigeek
 */
public interface BuildingService {

    /**
     * Creates a new building
     * @param building the new building
     * @return the newly created building
     * @throws BuildingAlreadyExistsException
     */
    public Building createBuilding(Building building) throws BuildingAlreadyExistsException;

    /**
     * Deletes a building by id
     * @param id the building's id to delete
     * @throws BuildingInexistantException
     * @throws BuildingInUseException 
     */
    public void deleteBuildingById(Integer id) throws BuildingInexistantException, BuildingInUseException;

    /**
     * Deletes a building
     * @param building the building to delete
     * @throws BuildingInUseException 
     */
    public void deleteBuilding(Building building) throws BuildingInUseException;

    /**
     * Retrieves all buildings stored in the database
     * @return a list of buildings or an empty list
     */
    public List<Building> getAllBuildings();

    /**
     * Retrieves a building by name
     * @param name the building's name
     * @return the corresponding building
     * @throws BuildingInexistantException 
     */
    public Building getBuildingByName(String name) throws BuildingInexistantException;

    /**
     * Retrieves a building by id
     * @param id the building's id
     * @return the corresponding building
     * @throws BuildingInexistantException 
     */
    public Building getBuildingByID(Integer id) throws BuildingInexistantException;

}
