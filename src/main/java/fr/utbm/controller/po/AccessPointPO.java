package fr.utbm.controller.po;

import java.util.ArrayList;

public class AccessPointPO {
    private String ap;
    private ArrayList<RssiPO> rssis;

    public AccessPointPO() {
    }

    public AccessPointPO(String ap, ArrayList<RssiPO> rssis) {
        this.ap = ap;
        this.rssis = rssis;
    }

    public String getAp() {
        return ap;
    }

    public ArrayList<RssiPO> getRssis() {
        return rssis;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public void setRssis(ArrayList<RssiPO> rssis) {
        this.rssis = rssis;
    }    
}
