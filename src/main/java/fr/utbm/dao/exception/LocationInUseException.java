package fr.utbm.dao.exception;

import java.util.Locale;
import javax.annotation.Resource;
import org.springframework.context.MessageSource;


public class LocationInUseException extends Exception {

        @Resource
    private MessageSource messageSource;
        
    private Integer id;
    
    public LocationInUseException(Integer id) {
        this.id = id;
    }
    
    public String getMessage(Locale l) {
        return messageSource.getMessage("LocationInUseException", new Object[] {id}, l);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }   
}
