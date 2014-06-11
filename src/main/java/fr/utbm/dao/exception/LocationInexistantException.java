package fr.utbm.dao.exception;

public class LocationInexistantException extends Exception {

    private Integer id;
    
    public LocationInexistantException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
