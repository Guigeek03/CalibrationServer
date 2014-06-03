package fr.utbm.service.impl;

import fr.utbm.dao.LocationDao;
import fr.utbm.dao.exception.LocationAlreadyExistsException;
import fr.utbm.dao.exception.LocationInUseException;
import fr.utbm.dao.exception.LocationInexistantException;
import fr.utbm.model.Location;
import fr.utbm.service.*;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultLocationService implements LocationService {

    @Resource
    SessionFactory sessionFactory;

    @Resource
    LocationDao locationDao;

    @Override
    public Location createLocation(Location location) throws LocationAlreadyExistsException {
        Location l = null;
        try {
            l = getLocationByID(location.getId());
        } catch (LocationInexistantException e) {
            locationDao.save(location);
            return location;
        }
        
        throw new LocationAlreadyExistsException(location.getId());
    }

    @Override
    public void deleteLocationById(Integer id) throws LocationInUseException, LocationInexistantException {
        Location location = locationDao.getLocationByID(id);
        if (location != null) {
            deleteLocation(location);
        } else {
            throw new LocationInexistantException(location.getId());
        }
    }

    @Override
    public void deleteLocation(Location location) throws LocationInUseException {
        try {
            locationDao.delete(location);
        } catch (ConstraintViolationException e) {
            throw new LocationInUseException(location.getId());
        }
    }

    @Override
    public List<Location> getAllLocations() {
        List<Location> list = locationDao.getAllLocations();
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    @Override
    public Location getLocationByID(Integer id) throws LocationInexistantException {
        Location location = locationDao.getLocationByID(id);
        if (location == null) {
            throw new LocationInexistantException(id);
        }
        return location;
    }

}
