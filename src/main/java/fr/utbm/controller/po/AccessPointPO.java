package fr.utbm.controller.po;

import java.util.ArrayList;

public class AccessPointPO {
    private String ap;
    private ArrayList<RssiPO> rssi;

    public AccessPointPO() {
    }

    public AccessPointPO(String ap, ArrayList<RssiPO> rssi) {
        this.ap = ap;
        this.rssi = rssi;
    }

    public String getAp() {
        return ap;
    }

    public ArrayList<RssiPO> getRssis() {
        return rssi;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public void setRssis(ArrayList<RssiPO> rssi) {
        this.rssi = rssi;
    }    
}
