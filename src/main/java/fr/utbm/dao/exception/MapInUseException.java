package fr.utbm.dao.exception;

import java.util.Locale;
import javax.annotation.Resource;
import org.springframework.context.MessageSource;


public class MapInUseException extends Exception {

    @Resource
    private MessageSource messageSource;
        
    private Integer id;
    private String name;
    private Integer building;
    
    public MapInUseException(Integer id, String name, Integer building) {
        this.id = id;
        this.name = name;
        this.building = building;
    }
    
    public String getMessage(Locale l) {
        return messageSource.getMessage("MapInUseException", null, l);
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
