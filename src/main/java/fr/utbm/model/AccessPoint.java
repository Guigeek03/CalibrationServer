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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Access point model object
 * 
 * @author Guigeek
 */
@Entity
@Table(name = "accessPoints")
public class AccessPoint implements Serializable {
    
    // Column names in the database
    public static final String ID = "id";
    public static final String MAC_ADDR = "macAddr";
    
    
    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name=MAC_ADDR, columnDefinition = "nvarchar(18)", nullable = false, unique = true)
    private String macAddr;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy=Rssi.ACCESS_POINT_ID)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Rssi> rssis = new HashSet<Rssi>();
    
    //Constructors
    public AccessPoint() {
    }

    public AccessPoint(Integer id, String macAddr) {
        this.id = id;
        this.macAddr = macAddr;
    }

    // Getters and setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
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
        hash = 59 * hash + (this.macAddr != null ? this.macAddr.hashCode() : 0);
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
        final AccessPoint other = (AccessPoint) obj;
        if ((this.macAddr == null) ? (other.macAddr != null) : !this.macAddr.equals(other.macAddr)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccessPoint{" + "id=" + id + ", macAddr=" + macAddr + '}';
    }   
}
