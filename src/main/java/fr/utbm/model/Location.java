package fr.utbm.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "locations")
public class Location implements Serializable {
    public static final String ID = "id";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String MAP = "map";
    public static final String RSSIS = "rssis";
    
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = X, nullable = false)
    private Double x;
    
    @Column(name = Y, nullable = false)
    private Double y;
    
    //@Column(name = MAP_ID, nullable = false)
    //private Integer mapId;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = MAP, nullable = false)
    private Map map;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = Rssi.LOCATION_ID)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Rssi> rssis = new HashSet<Rssi>();
    

    public Location() {
    }

    /**public Location(Integer id, Double x, Double y, Integer mapId) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.mapId = mapId;
    }**/
    public Location(Integer id, Double x, Double y, Map map) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.map = map;
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

    /**public Integer getMapId() {
    return mapId;
    }
    public void setMapId(Integer mapId) {
    this.mapId = mapId;
    }**/
    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    } 

    public Set<Rssi> getRssis() {
        return rssis;
    }

    public void setRssis(HashSet<Rssi> rssis) {
        this.rssis = rssis;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Location other = (Location) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", x=" + x + ", y=" + y + ", map=" + map.getId() + '}';
    }
}
