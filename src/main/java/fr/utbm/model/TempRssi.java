package fr.utbm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tempRssi")
public class TempRssi implements Serializable {
    
    public static final String ID = "id";
    public static final String ACCESS_POINT_ID = "idAp";
    public static final String CLIENT_MAC = "clientMac";
    public static final String AVERAGE_VALUE = "avgVal";

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = ACCESS_POINT_ID, nullable = false)
    private Integer accessPointId;
    
    @Column(name = CLIENT_MAC, columnDefinition = "nvarchar(18)", nullable = false)
    private String clientMac;
    
    @Column(name = AVERAGE_VALUE)
    private Double averageValue;
    
    public TempRssi() {
    }

    public TempRssi(Integer id, Integer accessPointId, String clientMac, Double averageValue) {
        this.id = id;
        this.accessPointId = accessPointId;
        this.clientMac = clientMac;
        this.averageValue = averageValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccessPointId() {
        return accessPointId;
    }

    public void setAccessPointId(Integer accessPointId) {
        this.accessPointId = accessPointId;
    }

    public String getClientMac() {
        return clientMac;
    }

    public void setClientMac(String clientMac) {
        this.clientMac = clientMac;
    }

    public Double getAverageValue() {
        return averageValue;
    }

    public void setAverageValue(Double averageValue) {
        this.averageValue = averageValue;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 17 * hash + (this.id != null ? this.id.hashCode() : 0);
        hash = 17 * hash + (this.accessPointId != null ? this.accessPointId.hashCode() : 0);
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
        final TempRssi other = (TempRssi) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        if (this.accessPointId != other.accessPointId && (this.accessPointId == null || !this.accessPointId.equals(other.accessPointId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TempRssi{" + "id=" + id + ", accessPointId=" + accessPointId + ", clientMac=" + clientMac + ", averageValue=" + averageValue + '}';
    }
}
