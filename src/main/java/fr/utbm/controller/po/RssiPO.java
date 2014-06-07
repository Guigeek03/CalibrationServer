package fr.utbm.controller.po;

public class RssiPO {
    private String macAddr;
    private Double value;
    private Integer nbSambles;

    public RssiPO() {
    }

    public RssiPO(String macAddr, Double value, Integer nbSambles) {
        this.macAddr = macAddr;
        this.value = value;
        this.nbSambles = nbSambles;
    }

    public String getMacAddr() {
        return macAddr;
    }

    public Integer getNbSambles() {
        return nbSambles;
    }

    public Double getValue() {
        return value;
    }

    public void setMacAddr(String macAddr) {
        this.macAddr = macAddr;
    }

    public void setNbSambles(Integer nbSambles) {
        this.nbSambles = nbSambles;
    }

    public void setValue(Double value) {
        this.value = value;
    }
}
