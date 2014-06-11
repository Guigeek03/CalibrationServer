package fr.utbm.dao.exception;

public class AccessPointAlreadyExistsException extends Exception {

    private final Integer id;
    private final String macAddr;

    public AccessPointAlreadyExistsException(Integer id, String macAddr) {
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
