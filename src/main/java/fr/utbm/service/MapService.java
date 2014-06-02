package fr.utbm.service;

import fr.utbm.dao.exception.MapAlreadyExistsException;
import fr.utbm.dao.exception.MapInUseException;
import fr.utbm.dao.exception.MapInexistantException;
import fr.utbm.model.Map;
import java.util.List;

public interface MapService {

    public Map createMap(Map map) throws MapAlreadyExistsException;

    public void deleteMapById(Integer id) throws MapInUseException, MapInexistantException;

    public void deleteMap(Map map) throws MapInUseException;

    public List<Map> getAllMaps();
    
    public List<Map> getMapsForBuildingID(Integer id);

    public Map getMapByName(String name);

    public Map getMapByID(Integer id);

}
