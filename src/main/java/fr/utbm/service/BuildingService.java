package fr.utbm.service;

import fr.utbm.dao.exception.BuildingAlreadyExistsException;
import fr.utbm.dao.exception.BuildingInUseException;
import fr.utbm.dao.exception.BuildingInexistantException;
import fr.utbm.model.Building;
import java.util.List;

public interface BuildingService {

    public Building createBuilding(Building building) throws BuildingAlreadyExistsException;

    public void updateBuilding(Integer id, String newName) throws BuildingAlreadyExistsException, BuildingInexistantException;

    public void deleteBuildingById(Integer id) throws BuildingInexistantException, BuildingInUseException;

    public void deleteBuilding(Building building) throws BuildingInUseException;

    public List<Building> getAllBuildings();

    public Building getBuildingByName(String name) throws BuildingInexistantException ;

    public Building getBuildingByID(Integer id) throws BuildingInexistantException ;

}
