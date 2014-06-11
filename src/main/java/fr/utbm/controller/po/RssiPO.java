package fr.utbm.controller.po;

/**
 * RSSI Presentation Objects for JSON responses
 * 
 * @author Guigeek
 */
public class RssiPO {
    private String mac;
    private Double value;
    private Integer samples;

    public RssiPO() {
    }

    public RssiPO(String mac, Double value, Integer samples) {
        this.mac = mac;
        this.value = value;
        this.samples = samples;
    }

    public String getMac() {
        return mac;
    }

    public Integer getSamples() {
        return samples;
    }

    public Double getValue() {
        return value;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public void setSamples(Integer samples) {
        this.samples = samples;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
