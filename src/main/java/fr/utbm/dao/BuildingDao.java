package fr.utbm.dao;

import fr.utbm.model.Building;
import java.util.List;

public interface BuildingDao extends Dao<Building> {
    public List<Building> getAllBuildings();

    public Building getBuildingByName(String name);
    
    public Building getBuildingByID(Integer id);  
}