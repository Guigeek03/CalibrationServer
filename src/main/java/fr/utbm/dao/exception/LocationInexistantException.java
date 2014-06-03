package fr.utbm.dao.exception;

import java.util.Locale;
import javax.annotation.Resource;
import org.springframework.context.MessageSource;

public class LocationInexistantException extends Exception {

    @Resource
    private MessageSource messageSource;
    
    private Integer id;
    
    public LocationInexistantException(Integer id) {
        this.id = id;
    }
    
    public String getMessage(Locale l) {
        return messageSource.getMessage("BuildingInexistantException", new Object[] {id}, l);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }   
}
