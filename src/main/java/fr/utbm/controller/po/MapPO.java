package fr.utbm.controller.po;

import fr.utbm.model.Map;

public class MapPO {
    
    private Integer id;
    private String name;
    private Integer nbPoints;

    public MapPO(Map m) {
        this.id = m.getId();
        this.name = m.getDescription();
        this.nbPoints = m.getLocations().size();
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

    public Integer getNbPoints() {
        return nbPoints;
    }

    public void setNbPoints(Integer nbPoints) {
        this.nbPoints = nbPoints;
    }    
}
