package fr.utbm.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "accessPoints")
public class AccessPoint implements Serializable {
    public static final String ID = "id";
    public static final String MAC_ADDR = "macAddr";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name=MAC_ADDR, columnDefinition = "nvarchar(18)", nullable = false)
    private String macAddr;

    public AccessPoint() {
    }

    public AccessPoint(Integer id, String macAddr) {
        this.id = id;
        this.macAddr = macAddr;
    }

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

    @Override
    public int hashCode() {
        int hash = 7;
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
        final AccessPoint other = (AccessPoint) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "AccessPoint{" + "id=" + id + ", macAddr=" + macAddr + '}';
    }   
}
