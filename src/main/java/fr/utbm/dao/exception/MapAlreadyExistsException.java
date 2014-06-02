package fr.utbm.dao.exception;

import fr.utbm.model.Building;
import java.util.Locale;
import javax.annotation.Resource;
import org.springframework.context.MessageSource;

/**
 *
 * @author Guigeek
 */
public class MapAlreadyExistsException extends Exception {

    @Resource
    private MessageSource messageSource;

    private Integer id;
    private String name;
    private Integer building;

    public MapAlreadyExistsException(Integer id, String name, Integer building) {
        this.id = id;
        this.name = name;
        this.building = building;
    }

    public String getMessage(Locale l) {
        return messageSource.getMessage("BuildingAlreadyExistsException", new Object[] {id, name, building}, l);
    }

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

    public Integer getBuilding() {
        return building;
    }

    public void setBuilding(Integer building) {
        this.building = building;
    }

    
}
