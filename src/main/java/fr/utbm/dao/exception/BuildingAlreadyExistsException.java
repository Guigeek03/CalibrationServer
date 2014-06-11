package fr.utbm.dao.exception;

public class BuildingAlreadyExistsException extends Exception {
    private final Integer id;
    private final String name;

    public BuildingAlreadyExistsException(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
