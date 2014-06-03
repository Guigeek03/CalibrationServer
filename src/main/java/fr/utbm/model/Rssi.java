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

@Entity
@Table(name = "rssi", uniqueConstraints = @UniqueConstraint(columnNames = {Rssi.ACCESS_POINT_ID, Rssi.LOCATION_ID}))
public class Rssi implements Serializable {

    public static final String ID = "id";
    public static final String ACCESS_POINT_ID = "accessPoint";
    public static final String LOCATION_ID = "location";
    public static final String AVERAGE_VALUE = "avgVal";
    public static final String STANDARD_DEVIATION = "stdDev";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**@Column(name = ACCESS_POINT_ID, nullable = false)
    private Integer accessPointId;

    @Column(name = LOCATION_ID)
    private Integer locationId;**/
    
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

    public Rssi() {
    }

    /** public Rssi(Integer accessPointId, Integer locationId, Double averageValue, Double standardDeviation) {
    this.accessPointId = accessPointId;
    this.locationId = locationId;
    this.averageValue = averageValue;
    this.standardDeviation = standardDeviation;
    }
    public Integer getAccessPointId() {
    return accessPointId;
    }
    public void setAccessPointId(Integer accessPointId) {
    this.accessPointId = accessPointId;
    }
    public Integer getLocationId() {
    return locationId;
    }
    public void setLocationId(Integer locationId) {
    this.locationId = locationId;
    }**/
    public Rssi(Location location, AccessPoint accessPoint, Double averageValue, Double standardDeviation) {
        this.location = location;
        this.accessPoint = accessPoint;
        this.averageValue = averageValue;
        this.standardDeviation = standardDeviation;
    }

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

    @Override
    public String toString() {
        return "Rssi{" + "id=" + id + ", location=" + location + ", accessPoint=" + accessPoint + ", averageValue=" + averageValue + ", standardDeviation=" + standardDeviation + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 41 * hash + (this.location != null ? this.location.hashCode() : 0);
        hash = 41 * hash + (this.accessPoint != null ? this.accessPoint.hashCode() : 0);
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.location != other.location && (this.location == null || !this.location.equals(other.location))) {
            return false;
        }
        if (this.accessPoint != other.accessPoint && (this.accessPoint == null || !this.accessPoint.equals(other.accessPoint))) {
            return false;
        }
        return true;
    }

    
}
