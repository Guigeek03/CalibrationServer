package fr.utbm.service;

import fr.utbm.dao.exception.RssiAlreadyExistsException;
import fr.utbm.dao.exception.RssiInUseException;
import fr.utbm.dao.exception.RssiInexistantException;
import fr.utbm.model.Rssi;
import java.util.List;

public interface RssiService {

    public Rssi createRssi(Rssi rssi) throws RssiAlreadyExistsException;

    public void deleteRssiById(Integer id) throws RssiInUseException, RssiInexistantException;

    public void deleteRssi(Rssi rssi) throws RssiInUseException;

    public List<Rssi> getAllRssis();

    public Rssi getRssiByID(Integer id) throws RssiInexistantException;

    public Rssi getRssiByAPAndLocation(Integer idAP, Integer idLoc) throws RssiInexistantException;

    public List<Rssi> getAllRssisByAP(Integer idAP);
}
