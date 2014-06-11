package fr.utbm.service;

import fr.utbm.dao.exception.RssiAlreadyExistsException;
import fr.utbm.dao.exception.RssiInUseException;
import fr.utbm.dao.exception.RssiInexistantException;
import fr.utbm.model.Rssi;
import java.util.List;

/**
 * Service interface for RSSI model object
 *
 * @author Guigeek
 */
public interface RssiService {

    /**
     * Creates a RSSI
     *
     * @param rssi the new rssi
     * @return the newly created rssi
     * @throws RssiAlreadyExistsException
     */
    public Rssi createRssi(Rssi rssi) throws RssiAlreadyExistsException;

    /**
     * Deletes a RSSI by id
     *
     * @param id the RSSI's id to delete
     * @throws RssiInUseException
     * @throws RssiInexistantException
     */
    public void deleteRssiById(Integer id) throws RssiInUseException, RssiInexistantException;

    /**
     * Deletes a RSSI
     *
     * @param rssi the RSSI to delete
     * @throws RssiInUseException
     */
    public void deleteRssi(Rssi rssi) throws RssiInUseException;

    /**
     * Retrieves all RSSIs stored in the database
     *
     * @return a list of RSSIs or an empty list
     */
    public List<Rssi> getAllRssis();

    /**
     * Retrieves a RSSI by id
     *
     * @param id the RSSI's id
     * @return the corresponding RSSI
     * @throws RssiInexistantException
     */
    public Rssi getRssiByID(Integer id) throws RssiInexistantException;

    /**
     * Retrieves a RSSI by Access Point and Location given in parameter
     *
     * @param idAP the RSSI's access point
     * @param idLoc the RSSI's location
     * @return the corresponding RSSI
     * @throws RssiInexistantException
     */
    public Rssi getRssiByAPAndLocation(Integer idAP, Integer idLoc) throws RssiInexistantException;

    /**
     * Retrieves all RSSI matching the access point given in parameter
     *
     * @param idAP the rssi's access point
     * @return a list of RSSI or an empty list
     */
    public List<Rssi> getAllRssisByAP(Integer idAP);
}
