package fr.utbm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "rssi", uniqueConstraints = @UniqueConstraint(columnNames = {Rssi.ACCESS_POINT_ID, Rssi.LOCATION_ID}))
public class Rssi implements Serializable {

    public static final String ID = "id";
    public static final String ACCESS_POINT_ID = "idAp";
    public static final String LOCATION_ID = "idLoc";
    public static final String AVERAGE_VALUE = "avgVal";
    public static final String STANDARD_DEVIATION = "stdDev";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = ACCESS_POINT_ID, nullable = false)
    private Integer accessPointId;

    @Column(name = LOCATION_ID)
    private Integer locationId;

    @Column(name = AVERAGE_VALUE)
    private Double averageValue;

    @Column(name = STANDARD_DEVIATION)
    private Double standardDeviation;

    public Rssi() {
    }

    public Rssi(Integer accessPointId, Integer locationId, Double averageValue, Double standardDeviation) {
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
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + (this.accessPointId != null ? this.accessPointId.hashCode() : 0);
        hash = 67 * hash + (this.locationId != null ? this.locationId.hashCode() : 0);
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
        if (this.accessPointId != other.accessPointId && (this.accessPointId == null || !this.accessPointId.equals(other.accessPointId))) {
            return false;
        }
        if (this.locationId != other.locationId && (this.locationId == null || !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Rssi{" + "accessPointId=" + accessPointId + ", locationId=" + locationId + ", averageValue=" + averageValue + ", standardDeviation=" + standardDeviation + '}';
    }
}
