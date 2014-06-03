package fr.utbm.dao.exception;


public class RssiInexistantException extends Exception {
    private final Integer id;
    private final Integer idLocation;
    private final Integer idAccessPoint;

    public RssiInexistantException(Integer id, Integer idLocation, Integer idAccessPoint) {
        this.id = id;
        this.idLocation = idLocation;
        this.idAccessPoint = idAccessPoint;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdLocation() {
        return idLocation;
    }

    public Integer getIdAccessPoint() {
        return idAccessPoint;
    }
}
