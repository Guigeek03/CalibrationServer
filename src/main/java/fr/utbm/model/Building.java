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
 * Building model object
 * 
 * @author Guigeek
 */
@Entity
@Table(name = "buildings")
public class Building implements Serializable {
    
    // Column names in the database
    public static final String ID = "id";
    public static final String NAME = "name";

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = NAME, columnDefinition = "nvarchar(100)", unique = true, nullable = false)
    private String name;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = Map.BUILDING_ID)
    @Fetch(FetchMode.SUBSELECT)
    private Set<Map> maps = new HashSet<Map>();
    
    // Constructors
    public Building() {
    }

    public Building(String name) {
        this.name = name;
    }
    
    // Getters and setters
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

    public Set<Map> getMaps() {
        return maps;
    }

    public void setMaps(Set<Map> maps) {
        this.maps = maps;
    }

    // hashCode, equals and toString override
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.name != null ? this.name.hashCode() : 0);
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
        final Building other = (Building) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Building{" + "id=" + id + ", name=" + name + '}';
    }
}
