package fr.utbm.dao.exception;

public class BuildingInUseException extends Exception {

    private final Integer id;
    private final String name;
    
    public BuildingInUseException(Integer id, String name) {
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
