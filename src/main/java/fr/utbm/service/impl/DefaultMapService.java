package fr.utbm.service.impl;

import fr.utbm.dao.BuildingDao;
import fr.utbm.dao.MapDao;
import fr.utbm.dao.exception.MapAlreadyExistsException;
import fr.utbm.dao.exception.MapInUseException;
import fr.utbm.dao.exception.MapInexistantException;
import fr.utbm.model.Map;
import fr.utbm.service.MapService;
import java.util.Collections;
import java.util.List;
import javax.annotation.Resource;
import org.hibernate.SessionFactory;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultMapService implements MapService {
    @Resource
    SessionFactory sessionFactory;
    
    @Resource
    MapDao mapDao;
    
    @Resource
    BuildingDao buildingDao;

    @Override
    public Map createMap(Map map) throws MapAlreadyExistsException {
        Map m = getMapByName(map.getDescription());
        if (!m.getDescription().equals("DEFAULT")) {
            throw new MapAlreadyExistsException(m.getId(), m.getDescription(), m.getBuilding());
        } else {
            mapDao.save(map);
        }
        return map;
    }

    @Override
    public void deleteMapById(Integer id) throws MapInUseException, MapInexistantException {
        Map map = mapDao.getMapByID(id);
        if (map != null) {
            deleteMap(map);
        } else {
            throw new MapInexistantException(id, "", null);
        }
    }

    @Override
    public void deleteMap(Map map) throws MapInUseException {
        try {
            mapDao.delete(map);
        } catch (ConstraintViolationException e) {
            throw new MapInUseException(map.getId(), map.getDescription(), map.getBuilding());
        }
    }

    @Override
    public List<Map> getAllMaps() {
        List<Map> list = mapDao.getAllMaps();
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }
    
    @Override
    public List<Map> getMapsForBuildingID(Integer id) {
        List<Map> list = mapDao.getMapsForBuildingID(id);
        if (list == null) {
            return Collections.EMPTY_LIST;
        }
        return list;
    }

    @Override
    public Map getMapByName(String name) {
        Map map = mapDao.getMapByName(name);
        if (map == null) {
            return new Map("DEFAULT", 0, 0, 0., 0., "", 0);
        }
        return map;
    }

    @Override
    public Map getMapByID(Integer id) {
        Map map = mapDao.getMapByID(id);
        if (map == null) {
            return new Map("DEFAULT", 0, 0, 0., 0., "", 0);
        }
        return map;
    }



}
