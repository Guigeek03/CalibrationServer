package fr.utbm.controller.po;

import fr.utbm.model.Location;

public class LocationPO {
    private Integer id;
    private Double x;
    private Double y;

    public LocationPO() {
    }

    public LocationPO(Location location) {
        id = location.getId();
        x = location.getX();
        y = location.getY();
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }
    
    
}
