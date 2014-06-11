package fr.utbm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Rssi model object
 * 
 * @author Guigeek
 */
@Entity
@Table(name = "rssi", uniqueConstraints = @UniqueConstraint(columnNames = {Rssi.ACCESS_POINT_ID, Rssi.LOCATION_ID}))
public class Rssi implements Serializable {

    // Column names in the database
    public static final String ID = "id";
    public static final String ACCESS_POINT_ID = "accessPoint";
    public static final String LOCATION_ID = "location";
    public static final String AVERAGE_VALUE = "avgVal";
    public static final String STANDARD_DEVIATION = "stdDev";

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = LOCATION_ID, nullable = false)
    private Location location;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = ACCESS_POINT_ID, nullable = false)
    private AccessPoint accessPoint;
    
    @Column(name = AVERAGE_VALUE)
    private Double averageValue;

    @Column(name = STANDARD_DEVIATION)
    private Double standardDeviation;

    // Constructors
    public Rssi() {
    }

    public Rssi(Location location, AccessPoint accessPoint, Double averageValue, Double standardDeviation) {
        this.location = location;
        this.accessPoint = accessPoint;
        this.averageValue = averageValue;
        this.standardDeviation = standardDeviation;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public AccessPoint getAccessPoint() {
        return accessPoint;
    }

    public void setAccessPoint(AccessPoint accessPoint) {
        this.accessPoint = accessPoint;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    public Double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    // hashCode, equals and toString()
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + (this.location != null ? this.location.hashCode() : 0);
        hash = 43 * hash + (this.accessPoint != null ? this.accessPoint.hashCode() : 0);
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
        final Rssi other = (Rssi) obj;
        if (this.location != other.location && (this.location == null || !this.location.equals(other.location))) {
            return false;
        }
        if (this.accessPoint != other.accessPoint && (this.accessPoint == null || !this.accessPoint.equals(other.accessPoint))) {
            return false;
        }
        return true;
    }
    
    @Override
    public String toString() {
        return "Rssi{" + "id=" + id + ", location=" + location + ", accessPoint=" + accessPoint + ", averageValue=" + averageValue + ", standardDeviation=" + standardDeviation + '}';
    }

}
