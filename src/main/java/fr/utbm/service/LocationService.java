package fr.utbm.service;

import fr.utbm.dao.exception.LocationAlreadyExistsException;
import fr.utbm.dao.exception.LocationInUseException;
import fr.utbm.dao.exception.LocationInexistantException;
import fr.utbm.model.Location;
import java.util.List;

/**
 * Service interface for Location model object
 *
 * @author Guigeek
 */
public interface LocationService {

    /**
     * Creates a new location
     *
     * @param location the new location
     * @return the newly created location
     * @throws LocationAlreadyExistsException
     */
    public Location createLocation(Location location) throws LocationAlreadyExistsException;

    /**
     * Deletes a location by id
     *
     * @param id the location's id to delete
     * @throws LocationInUseException
     * @throws LocationInexistantException
     */
    public void deleteLocationById(Integer id) throws LocationInUseException, LocationInexistantException;

    /**
     * Deletes a location
     *
     * @param location the location to delete
     * @throws LocationInUseException
     */
    public void deleteLocation(Location location) throws LocationInUseException;

    /**
     * Retrieves all locations stored in the database
     *
     * @return a list of locations or an empty list
     */
    public List<Location> getAllLocations();

    /**
     * Retrieves all locations with map id
     *
     * @param mapId the location's map
     * @return a list of locations or an empty list
     */
    public List<Location> getLocationByMap(Integer mapId);

    /**
     * Retrieves a location by id
     *
     * @param id the location's id
     * @return the corresponding location
     * @throws LocationInexistantException
     */
    public Location getLocationByID(Integer id) throws LocationInexistantException;

}
