package fr.utbm.service.impl;

import fr.utbm.dao.exception.BuildingAlreadyExistsException;
import fr.utbm.dao.BuildingDao;
import fr.utbm.dao.exception.BuildingInUseException;
import fr.utbm.dao.exception.BuildingInexistantException;
import fr.utbm.service.*;
import fr.utbm.model.Building;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultBuildingService implements BuildingService {    
    @Resource
    SessionFactory sessionFactory;
    
    @Resource
    BuildingDao buildingDao;

    @Override
    public Building createBuilding(Building building) throws BuildingAlreadyExistsException {
        if (buildingDao.getAllBuildings().contains(building)) {
            throw new BuildingAlreadyExistsException(building.getId(), building.getName());
        }
        
        buildingDao.save(building);
        return building;
    }

    @Override
    public Building getBuildingByID(Integer id) throws BuildingInexistantException {
        Building b = buildingDao.getBuildingByID(id);
        if (b == null) {
            throw new BuildingInexistantException(id, "");
        }
        return b;
    }

    @Override
    public void deleteBuildingById(Integer id) throws BuildingInexistantException, BuildingInUseException {
        Building building = buildingDao.getBuildingByID(id);
        if (building != null) {
            deleteBuilding(building);
        } else {
            throw new BuildingInexistantException(id, "");
        }
    }

    @Override
    public void deleteBuilding(Building building) throws BuildingInUseException {
        try {
            buildingDao.delete(building);
        } catch (ConstraintViolationException e) {
            throw new BuildingInUseException(building.getId(), building.getName());
        }
    }

    @Override
    public List<Building> getAllBuildings() {
        List<Building> list = buildingDao.getAllBuildings();
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    @Override
    public Building getBuildingByName(String name) throws BuildingInexistantException {
        Building b = buildingDao.getBuildingByName(name);
        if (b == null) {
            throw new BuildingInexistantException(0, name);
        }
        return b;
    }  
}
