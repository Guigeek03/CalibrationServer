package fr.utbm.dao;

import fr.utbm.model.TempRssi;
import java.util.List;

public interface TempRssiDao extends Dao<TempRssi> {
    public List<TempRssi> getAllTempRssis();
    
    public TempRssi getTempRssiByID(Integer id);  
    
    public TempRssi getTempRssiByAP(Integer idAP);
}