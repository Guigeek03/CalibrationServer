package fr.utbm.dao;

import fr.utbm.model.Location;
import java.util.List;

public interface LocationDao extends Dao<Location> {
    public List<Location> getAllLocations();
    
    public List<Location> getLocationByMap(Integer mapId);
    
    public Location getLocationByID(Integer id);  
}