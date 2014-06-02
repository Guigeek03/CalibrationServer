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
        Building b = getBuildingByName(building.getName());
        if (!b.getName().equals("DEFAULT")) {
            throw new BuildingAlreadyExistsException(building.getId(), building.getName());
        } else {
            buildingDao.save(building);
        }
        return building;
    }

    @Override
    public Building getBuildingByID(Integer id) {
        Building b = buildingDao.getBuildingByID(id);
        if (b == null) {
            return new Building(0, "DEFAULT");
        }
        return b;
    }

    @Override
    public void updateBuilding(Integer id, String newName) throws BuildingAlreadyExistsException, BuildingInexistantException {
        Building building = buildingDao.getBuildingByID(id);
        if (!building.getName().equals("DEFAULT")) {
            building.setName(newName);
            try {
                buildingDao.save(building);
            } catch (ConstraintViolationException e) {
                throw new BuildingAlreadyExistsException(id, newName);
            }
        }
        else {
            throw new BuildingInexistantException(id, "");
        }
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
    public void deleteBuilding(Building building) throws BuildingInexistantException, BuildingInUseException {
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
    public Building getBuildingByName(String name) {
        Building b = buildingDao.getBuildingByName(name);
        if (b == null) {
            return new Building(0, "DEFAULT");
        }
        return b;
    }  
}
