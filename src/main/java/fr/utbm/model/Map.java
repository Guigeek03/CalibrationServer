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

@Entity
@Table(name = "maps", uniqueConstraints = @UniqueConstraint(columnNames = {Map.BUILDING_ID, Map.DESCRIPTION}))
public class Map implements Serializable {

    public static final String ID = "id";
    public static final String DESCRIPTION = "description";
    public static final String PX_WIDTH = "pxWidth";
    public static final String PX_HEIGHT = "pxHeight";
    public static final String METERS_WIDTH = "metersWidth";
    public static final String METERS_HEIGHT = "metersHeight";
    public static final String IMAGE_FILE = "imageFile";
    public static final String BUILDING_ID = "building";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = DESCRIPTION, columnDefinition = "nvarchar(100)", nullable = false)
    private String description;

    @Column(name = PX_WIDTH, nullable = false)
    private Integer pxWidth;
    
    @Column(name = PX_HEIGHT, nullable = false)
    private Integer pxHeight;
    
    @Column(name = METERS_WIDTH)
    private Double metersWidth;
    
    @Column(name = METERS_HEIGHT)
    private Double metersHeight;
    
    @Column(name = IMAGE_FILE, columnDefinition = "nvarchar(1000)")
    private String imageFile;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = BUILDING_ID, nullable = false)
    private Building building;
    //@Column(name = BUILDING_ID, nullable = false)
    //private Integer building;
    
    @OneToMany(fetch = FetchType.LAZY, mappedBy=Location.MAP)
    private Set<Location> locations = new HashSet<Location>();
   
    public Map() {
        this.description = "";
        this.pxWidth = 0;
        this.pxHeight = 0;
        this.metersWidth = 0.;
        this.metersHeight = 0.;
        this.imageFile = "";
    }

   //public Map(String description, Integer pxWidth, Integer pxHeight, Double metersWidth, Double metersHeight, String imageFile, Integer building) {
   public Map(String description, Integer pxWidth, Integer pxHeight, Double metersWidth, Double metersHeight, String imageFile, Building building) {
        this.description = description;
        this.pxWidth = pxWidth;
        this.pxHeight = pxHeight;
        this.metersWidth = metersWidth;
        this.metersHeight = metersHeight;
        this.imageFile = imageFile;
        this.building = building;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 97 * hash + (this.building != null ? this.building.hashCode() : 0);
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
        final Map other = (Map) obj;
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if (this.building != other.building && (this.building == null || !this.building.equals(other.building))) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPxWidth() {
        return pxWidth;
    }

    public void setPxWidth(Integer pxWidth) {
        this.pxWidth = pxWidth;
    }

    public Integer getPxHeight() {
        return pxHeight;
    }

    public void setPxHeight(Integer pxHeight) {
        this.pxHeight = pxHeight;
    }

    public Double getMetersWidth() {
        return metersWidth;
    }

    public void setMetersWidth(Double metersWidth) {
        this.metersWidth = metersWidth;
    }

    public Double getMetersHeight() {
        return metersHeight;
    }

    public void setMetersHeight(Double metersHeight) {
        this.metersHeight = metersHeight;
    }

    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public Set<Location> getLocations() {
        return locations;
    }

    public void setLocations(Set<Location> locations) {
        this.locations = locations;
    }
}
