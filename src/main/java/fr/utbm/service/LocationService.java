package fr.utbm.service;

import fr.utbm.dao.exception.LocationAlreadyExistsException;
import fr.utbm.dao.exception.LocationInUseException;
import fr.utbm.dao.exception.LocationInexistantException;
import fr.utbm.model.Location;
import java.util.List;

public interface LocationService {

    public Location createLocation(Location location) throws LocationAlreadyExistsException;

    public void deleteLocationById(Integer id)  throws LocationInUseException, LocationInexistantException;

    public void deleteLocation(Location location) throws LocationInUseException;

    public List<Location> getAllLocations();

    public Location getLocationByID(Integer id)  throws LocationInexistantException ;

}
