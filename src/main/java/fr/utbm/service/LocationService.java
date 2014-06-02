package fr.utbm.service;

import fr.utbm.model.Location;
import java.util.List;

public interface LocationService {

    public Location createLocation(Location location);

    public void updateLocation(Integer id, Double newX, Double newY, Integer newMapID);

    public void deleteLocationById(Integer id);

    public void deleteLocation(Location location);

    public List<Location> getAllLocations();

    public Location getLocationByID(Integer id);

}
