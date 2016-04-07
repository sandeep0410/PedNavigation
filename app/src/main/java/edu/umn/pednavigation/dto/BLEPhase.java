package edu.umn.pednavigation.dto;

/**
 * Created by Sandeep on 3/23/2016.
 */
public class BLEPhase {

    String intx_id;
    String mac;
    String dir;
    int phase;

    public BLEPhase(String intx_id, String mac, String dir, int phase) {
        this.intx_id = intx_id;
        this.mac = mac;
        this.dir = dir;
        this.phase = phase;
    }

    public BLEPhase() {
        this.intx_id = "-1";
        this.mac = "";
        this.dir = "";
        this.phase = -1;
    }

    public String getIntx_id() {
        return intx_id;
    }

    public void setIntx_id(String intx_id) {
        this.intx_id = intx_id;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public int getPhase() {
        return phase;
    }

    public void setPhase(int phase) {
        this.phase = phase;
    }

    @Override
    public String toString() {
        return "intx_id: " + intx_id + " mac: " + mac + " dir: " + dir + " phase: " + phase;
    }
}
