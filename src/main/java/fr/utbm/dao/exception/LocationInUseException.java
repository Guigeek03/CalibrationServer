package fr.utbm.dao.exception;

public class LocationInUseException extends Exception {

    private Integer id;
    
    public LocationInUseException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
