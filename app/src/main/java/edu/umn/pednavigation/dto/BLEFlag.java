package edu.umn.pednavigation.dto;

/**
 * Created by Sandeep on 3/3/2016.
 */
public class BLEFlag {
    private String mac = null;
    private int flag = -1;

    public BLEFlag(String mac, int flag) {
        this.mac = mac;
        this.flag = flag;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return " MACID: " + mac + " flag: " + flag;
    }
}
