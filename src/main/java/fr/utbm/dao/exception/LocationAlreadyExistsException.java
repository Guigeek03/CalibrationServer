package fr.utbm.dao.exception;

public class LocationAlreadyExistsException extends Exception {

    private Integer id;

    public LocationAlreadyExistsException(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
