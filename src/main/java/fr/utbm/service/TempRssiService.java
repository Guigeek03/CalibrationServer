package fr.utbm.service;

import fr.utbm.model.TempRssi;
import java.util.List;

public interface TempRssiService {

    public TempRssi createRssi(TempRssi tempRssi);

    public void updateTempRssi(Integer id, Integer newIdAP, Double newAverageValue, String newClientMacAddr);

    public void deleteTempRssiById(Integer id);

    public void deleteTempRssi(TempRssi tempRssi);

    public List<TempRssi> getAllTempRssis();
    
    public TempRssi getTempRssiByID(Integer id);  
    
    public TempRssi getTempRssiByAP(Integer idAP);
}
