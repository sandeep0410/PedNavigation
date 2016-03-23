package edu.umn.pednavigation.dto;

/**
 * Created by Sandeep on 3/3/2016.
 */
public class BLEPed {
    private String mac;
    private double latitude;
    private double longitude;
    private String id1;
    private String d1;
    private int d11;
    private int xing1;
    private String streetinfo1;
    private String id2;
    private String d2;
    private int d22;
    private int xing2;
    private String streetinfo2;
    private String id3;
    private String d3;
    private int d33;
    private int xing3;
    private String streetinfo3;
    private String id4;
    private String d4;
    private int d44;
    private int xing4;
    private String streetinfo4;
    private String desc;
    private int bt_present;

    public BLEPed() {

    }

    public BLEPed(String mac, double latitude, double longitude, String id1, String d1, int d11, int xing1, String streetinfo1, String id2, String d2, int d22, int xing2, String streetinfo2, String id3, String d3, int d33, int xing3, String streetinfo3, String id4, String d4, int d44, int xing4, String streetinfo4, String desc, int bt_present) {
        this.mac = mac;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id1 = id1;
        this.d1 = d1;
        this.d11 = d11;
        this.xing1 = xing1;
        this.streetinfo1 = streetinfo1;
        this.id2 = id2;
        this.d2 = d2;
        this.d22 = d22;
        this.xing2 = xing2;
        this.streetinfo2 = streetinfo2;
        this.id3 = id3;
        this.d3 = d3;
        this.d33 = d33;
        this.xing3 = xing3;
        this.streetinfo3 = streetinfo3;
        this.id4 = id4;
        this.d4 = d4;
        this.d44 = d44;
        this.xing4 = xing4;
        this.streetinfo4 = streetinfo4;
        this.desc = desc;
        this.bt_present = bt_present;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getD1() {
        return d1;
    }

    public void setD1(String d1) {
        this.d1 = d1;
    }

    public int getD11() {
        return d11;
    }

    public void setD11(int d11) {
        this.d11 = d11;
    }

    public int getXing1() {
        return xing1;
    }

    public void setXing1(int xing1) {
        this.xing1 = xing1;
    }

    public String getStreetinfo1() {
        return streetinfo1;
    }

    ;

    public void setStreetinfo1(String streetinfo1) {
        this.streetinfo1 = streetinfo1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    public String getD2() {
        return d2;
    }

    public void setD2(String d2) {
        this.d2 = d2;
    }

    public int getD22() {
        return d22;
    }

    public void setD22(int d22) {
        this.d22 = d22;
    }

    public int getXing2() {
        return xing2;
    }

    public void setXing2(int xing2) {
        this.xing2 = xing2;
    }

    public String getStreetinfo2() {
        return streetinfo1;
    }

    ;

    public void setStreetinfo2(String streetinfo2) {
        this.streetinfo2 = streetinfo2;
    }

    public String getId3() {
        return id3;
    }

    public void setId3(String id3) {
        this.id3 = id3;
    }

    public String getD3() {
        return d3;
    }

    public void setD3(String d3) {
        this.d3 = d3;
    }

    public int getD33() {
        return d33;
    }

    public void setD33(int d33) {
        this.d33 = d33;
    }

    public int getXing3() {
        return xing3;
    }

    public void setXing3(int xing3) {
        this.xing3 = xing3;
    }

    public String getStreetinfo3() {
        return streetinfo3;
    }

    public void setStreetinfo3(String streetinfo3) {
        this.streetinfo3 = streetinfo3;
    }

    public String getId4() {
        return id4;
    }

    public void setId4(String id4) {
        this.id4 = id4;
    }

    public String getD4() {
        return d4;
    }

    public void setD4(String d4) {
        this.d4 = d4;
    }

    public int getD44() {
        return d44;
    }

    public void setD44(int d44) {
        this.d44 = d44;
    }

    public int getXing4() {
        return xing4;
    }

    public void setXing4(int xing4) {
        this.xing4 = xing4;
    }

    public String getStreetinfo4() {
        return streetinfo4;
    }

    public void setStreetinfo4(String streetinfo4) {
        this.streetinfo4 = streetinfo4;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getBt_present() {
        return bt_present;
    }

    public void setBt_present(int bt_present) {
        this.bt_present = bt_present;
    }

    public String toString() {
        return "MAC_ID: " + mac + " latitude: " + latitude + " longitude: " + longitude
                + " id1: " + id1 + " d1: " + d1 + " d11: " + d11 + " xing1: " + xing1 + " streetinfo1: " + streetinfo1
                + " id2: " + id2 + " d2: " + d2 + " d22: " + d22 + " xing2: " + xing2 + " streetinfo2: " + streetinfo2
                + " id3: " + id3 + " d3: " + d3 + " d33: " + d33 + " xing3: " + xing3 + " streetinfo3: " + streetinfo3
                + " id4: " + id4 + " d4: " + d4 + " d44: " + d44 + " xing4: " + xing4 + " streetinfo4: " + streetinfo4
                + " desc: " + desc + " bt_present: " + bt_present;
    }
}
