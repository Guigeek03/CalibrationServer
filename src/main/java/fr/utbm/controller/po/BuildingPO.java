package fr.utbm.controller.po;

import fr.utbm.model.Building;

public class BuildingPO {
    
    private Integer id;
    private String name;
    private Integer nbFloors;

    public BuildingPO(Building b, Integer nbFloors) {
        this.id = b.getId();
        this.name = b.getName();
        this.nbFloors = nbFloors;
    }    

    public BuildingPO(Building b) {
        this.id = b.getId();
        this.name = b.getName();
        this.nbFloors = b.getMaps().size();
    }    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNbFloors() {
        return nbFloors;
    }

    public void setNbFloors(Integer nbFloors) {
        this.nbFloors = nbFloors;
    }    
}
