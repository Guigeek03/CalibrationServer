package fr.utbm.dao.exception;

import java.util.Locale;
import javax.annotation.Resource;
import org.springframework.context.MessageSource;

/**
 *
 * @author Guigeek
 */
public class AccessPointAlreadyExistsException extends Exception {

    @Resource
    private MessageSource messageSource;

    private Integer id;
    private String macAddr;

    public AccessPointAlreadyExistsException(Integer id, String macAddr) {
        this.id = id;
        this.macAddr = macAddr;
    }

    public String getMessage(Locale l) {
        return messageSource.getMessage("AccessPointAlreadyExistsException", new Object[] {id, macAddr}, l);
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

}
