package edu.umn.pednavigation.dto;

/**
 * Created by Sandeep on 10/3/2015.
 */
public class BLETag {
    String bleMac;
    double lat;
    double lon;
    String streetinfo1;
    String streetinfo2;
    String streetinfo31;
    String streetinfo32;
    String streetinfo4;

    public BLETag() {
    }

    public BLETag(String bleMac, double lat, double lon, String streetinfo1, String streetinfo2, String streetinfo31, String streetinfo32, String streetinfo4) {
        this.bleMac = bleMac;
        this.lat = lat;
        this.lon = lon;
        this.streetinfo1 = streetinfo1;
        this.streetinfo2 = streetinfo2;
        this.streetinfo31 = streetinfo31;
        this.streetinfo32 = streetinfo32;
        this.streetinfo4 = streetinfo4;
    }

    public String getBleMac() {
        return bleMac;
    }

    public void setBleMac(String bleMac) {
        this.bleMac = bleMac;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getStreetinfo1() {
        return streetinfo1;
    }

    public void setStreetinfo1(String streetinfo1) {
        this.streetinfo1 = streetinfo1;
    }

    public String getStreetinfo2() {
        return streetinfo2;
    }

    public void setStreetinfo2(String streetinfo2) {
        this.streetinfo2 = streetinfo2;
    }

    public String getStreetinfo31() {
        return streetinfo31;
    }

    public void setStreetinfo31(String streetinfo31) {
        this.streetinfo31 = streetinfo31;
    }

    public String getStreetinfo32() {
        return streetinfo32;
    }

    public void setStreetinfo32(String streetinfo32) {
        this.streetinfo32 = streetinfo32;
    }

    public String getStreetinfo4() {
        return streetinfo4;
    }

    public void setStreetinfo4(String streetinfo4) {
        this.streetinfo4 = streetinfo4;
    }

    @Override
    public String toString() {
        return "BLETag{" +
                "bleMac='" + bleMac + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", streetinfo1='" + streetinfo1 + '\'' +
                ", streetinfo2='" + streetinfo2 + '\'' +
                ", streetinfo31='" + streetinfo31 + '\'' +
                ", streetinfo32='" + streetinfo32 + '\'' +
                ", streetinfo4='" + streetinfo4 + '\'' +
                '}';
    }
}
