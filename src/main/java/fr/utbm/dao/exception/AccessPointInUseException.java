package fr.utbm.dao.exception;

public class AccessPointInUseException extends Exception {
    
    private final Integer id;
    private final String macAddr;
    
    public AccessPointInUseException(Integer id, String macAddr) {
        this.id = id;
        this.macAddr = macAddr;
    }

    public Integer getId() {
        return id;
    }

    public String getMacAddr() {
        return macAddr;
    }    
}
