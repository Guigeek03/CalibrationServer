package fr.utbm.dao;

import fr.utbm.model.Rssi;
import java.util.List;

public interface RssiDao extends Dao<Rssi> {
    public List<Rssi> getAllRssis();
    
    public Rssi getRssiByID(Integer id);  
    
    public Rssi getRssiByAPAndLocation(Integer idAP, Integer idLoc);
    
    public List<Rssi> getAllRssisByAP(Integer idAP);
}