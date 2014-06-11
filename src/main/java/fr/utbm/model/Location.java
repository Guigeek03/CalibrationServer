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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Location model object
 * 
 * @author Guigeek
 */
@Entity
@Table(name = "locations", uniqueConstraints = @UniqueConstraint(columnNames = {Location.X, Location.Y, Location.MAP}))
public class Location implements Serializable {
    
    // Column names in the database
    public static final String ID = "id";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String MAP = "map";
    public static final String RSSIS = "rssis";
    
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = X, nullable = false)
    private Double x;
    
    @Column(name = Y, nullable = false)
    private Double y;
        
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = MAP, nullable = false)
    private Map map;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = Rssi.LOCATION_ID)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Rssi> rssis = new HashSet<Rssi>();
    
    // Constructors
    public Location() {
    }

    public Location(Integer id, Double x, Double y, Map map) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.map = map;
    }    

    // Getters and setters
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

    // hashCode, equals and toString override
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.x != null ? this.x.hashCode() : 0);
        hash = 17 * hash + (this.y != null ? this.y.hashCode() : 0);
        hash = 17 * hash + (this.map != null ? this.map.hashCode() : 0);
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
        if (this.x != other.x && (this.x == null || !this.x.equals(other.x))) {
            return false;
        }
        if (this.y != other.y && (this.y == null || !this.y.equals(other.y))) {
            return false;
        }
        if (this.map != other.map && (this.map == null || !this.map.equals(other.map))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", x=" + x + ", y=" + y + ", map=" + map.getId() + '}';
    }
}
