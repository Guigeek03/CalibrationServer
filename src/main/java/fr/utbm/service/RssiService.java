package fr.utbm.service;

import fr.utbm.model.Rssi;
import java.util.List;

public interface RssiService {

    public Rssi createRssi(Rssi rssi);

    public void updateRssi(Integer id, Integer newIdLoc, Integer newIdAP, Double newAverageValue, Double newStdDeviation);

    public void deleteRssiById(Integer id);

    public void deleteRssi(Rssi rssi);

    public List<Rssi> getAllRssis();

    public Rssi getRssiByID(Integer id);
    
    public Rssi getRssiByAPAndLocation(Integer idAP, Integer idLoc);
}
