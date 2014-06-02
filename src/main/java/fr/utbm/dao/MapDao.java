package fr.utbm.dao;

import fr.utbm.model.Map;
import java.util.List;

public interface MapDao extends Dao<Map> {

    public List<Map> getAllMaps();

    public List<Map> getMapsForBuildingID(Integer id);

    public Map getMapByName(String name);

    public Map getMapByID(Integer id);
}
