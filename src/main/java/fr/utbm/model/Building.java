package fr.utbm.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "buildings")
public class Building implements Serializable {
    public static final String ID = "id";
    public static final String NAME = "name";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = NAME, columnDefinition = "nvarchar(100)", unique = true, nullable = false)
    private String name;
    
    /**
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "building")
    private Set<Map> maps = new HashSet<Map>(0);
    **/
    public Building() {
    }

    public Building(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    
    /**public Building(Integer id, String name, Set<Map> maps) {
        this.id = id;
        this.name = name;
        this.maps = maps;
    }**/

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

    /**public Set<Map> getMaps() {
        return maps;
    }

    public void setMaps(Set<Map> maps) {
        this.maps = maps;
    }**/
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + (this.id != null ? this.id.hashCode() : 0);
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
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
