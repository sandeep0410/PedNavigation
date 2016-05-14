package edu.umn.pednavigation.dto;

/**
 * Created by Sandeep on 3/3/2016.
 */
public class BLEPed {


    private String mac;
    private double latitude;
    private double longitude;

    // Adding changes after modification in the table
    private String idNorth;
    private String dirNorth;
    private int dirIntNorth;
    private int xingNorth;
    private String streetinfoNorth;

    private String idEast;
    private String dirEast;
    private int dirIntEast;
    private int xingEast;
    private String streetinfoEast;

    private String idSouth;
    private String dirSouth;
    private int dirIntSouth;
    private int xingSouth;
    private String streetinfoSouth;

    private String idWest;
    private String dirWest;
    private int dirIntWest;
    private int xingWest;
    private String streetinfoWest;

    private String idNortheast;
    private String dirNortheast;
    private int dirIntNortheast;
    private int xingNortheast;
    private String streetinfoNortheast;

    private String idSoutheast;
    private String dirSoutheast;
    private int dirIntSoutheast;
    private int xingSoutheast;
    private String streetinfoSoutheast;

    private String idSouthwest;
    private String dirSouthwest;
    private int dirIntSouthwest;
    private int xingSouthwest;
    private String streetinfoSouthwest;

    private String idNorthwest;
    private String dirNorthwest;
    private int dirIntNorthwest;
    private int xingNorthwest;
    private String streetinfoNorthwest;
    private String desc;
    private int bt_present;

    public BLEPed() {

    }

    public BLEPed(String mac, double latitude, double longitude, String idNorth, String dirNorth, int dirIntNorth,
                  int xingNorth, String streetinfoNorth, String idEast, String dirEast, int dirIntEast, int xingEast,
                  String streetinfoEast, String idSouth, String dirSouth, int dirIntSouth, int xingSouth,
                  String streetinfoSouth, String idWest, String dirWest, int dirIntWest, int xingWest, String streetinfoWest,
                  String idNortheast, String dirNortheast, int dirIntNortheast, int xingNortheast, String streetinfoNortheast,
                  String idSoutheast, String dirSoutheast, int dirIntSoutheast, int xingSoutheast, String streetinfoSoutheast,
                  String idSouthwest, String dirSouthwest, int dirIntSouthwest, int xingSouthwest, String streetinfoSouthwest,
                  String idNorthwest, String dirNorthwest, int dirIntNorthwest, int xingNorthwest, String streetinfoNorthwest,
                  String desc, int bt_present) {
        this.mac = mac;
        this.latitude = latitude;
        this.longitude = longitude;
        this.idNorth = idNorth;
        this.dirNorth = dirNorth;
        this.dirIntNorth = dirIntNorth;
        this.xingNorth = xingNorth;
        this.streetinfoNorth = streetinfoNorth;
        this.idEast = idEast;
        this.dirEast = dirEast;
        this.dirIntEast = dirIntEast;
        this.xingEast = xingEast;
        this.streetinfoEast = streetinfoEast;
        this.idSouth = idSouth;
        this.dirSouth = dirSouth;
        this.dirIntSouth = dirIntSouth;
        this.xingSouth = xingSouth;
        this.streetinfoSouth = streetinfoSouth;
        this.idWest = idWest;
        this.dirWest = dirWest;
        this.dirIntWest = dirIntWest;
        this.xingWest = xingWest;
        this.streetinfoWest = streetinfoWest;
        this.idNortheast = idNortheast;
        this.dirNortheast = dirNortheast;
        this.dirIntNortheast = dirIntNortheast;
        this.xingNortheast = xingNortheast;
        this.streetinfoNortheast = streetinfoNortheast;
        this.idSoutheast = idSoutheast;
        this.dirSoutheast = dirSoutheast;
        this.dirIntSoutheast = dirIntSoutheast;
        this.xingSoutheast = xingSoutheast;
        this.streetinfoSoutheast = streetinfoSoutheast;
        this.idSouthwest = idSouthwest;
        this.dirSouthwest = dirSouthwest;
        this.dirIntSouthwest = dirIntSouthwest;
        this.xingSouthwest = xingSouthwest;
        this.streetinfoSouthwest = streetinfoSouthwest;
        this.idNorthwest = idNorthwest;
        this.dirNorthwest = dirNorthwest;
        this.dirIntNorthwest = dirIntNorthwest;
        this.xingNorthwest = xingNorthwest;
        this.streetinfoNorthwest = streetinfoNorthwest;
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

    public String getIdNorth() {
        return idNorth;
    }

    public void setIdNorth(String idNorth) {
        this.idNorth = idNorth;
    }

    public String getDirNorth() {
        return dirNorth;
    }

    public void setDirNorth(String dirNorth) {
        this.dirNorth = dirNorth;
    }

    public int getDirIntNorth() {
        return dirIntNorth;
    }

    public void setDirIntNorth(int dirIntNorth) {
        this.dirIntNorth = dirIntNorth;
    }

    public int getXingNorth() {
        return xingNorth;
    }

    public void setXingNorth(int xingNorth) {
        this.xingNorth = xingNorth;
    }

    public String getStreetinfoNorth() {
        return streetinfoNorth;
    }

    public void setStreetinfoNorth(String streetinfoNorth) {
        this.streetinfoNorth = streetinfoNorth;
    }

    public String getIdNorthwest() {
        return idNorthwest;
    }

    public void setIdNorthwest(String idNorthwest) {
        this.idNorthwest = idNorthwest;
    }

    public String getDirNorthwest() {
        return dirNorthwest;
    }

    public void setDirNorthwest(String dirNorthwest) {
        this.dirNorthwest = dirNorthwest;
    }

    public int getDirIntNorthwest() {
        return dirIntNorthwest;
    }

    public void setDirIntNorthwest(int dirIntNorthwest) {
        this.dirIntNorthwest = dirIntNorthwest;
    }

    public int getXingNorthwest() {
        return xingNorthwest;
    }

    public void setXingNorthwest(int xingNorthwest) {
        this.xingNorthwest = xingNorthwest;
    }

    public String getStreetinfoNorthwest() {
        return streetinfoNorthwest;
    }

    public void setStreetinfoNorthwest(String streetinfoNorthwest) {
        this.streetinfoNorthwest = streetinfoNorthwest;
    }

    public String getIdWest() {
        return idWest;
    }

    public void setIdWest(String idWest) {
        this.idWest = idWest;
    }

    public String getDirWest() {
        return dirWest;
    }

    public void setDirWest(String dirWest) {
        this.dirWest = dirWest;
    }

    public int getDirIntWest() {
        return dirIntWest;
    }

    public void setDirIntWest(int dirIntWest) {
        this.dirIntWest = dirIntWest;
    }

    public int getXingWest() {
        return xingWest;
    }

    public void setXingWest(int xingWest) {
        this.xingWest = xingWest;
    }

    public String getStreetinfoWest() {
        return streetinfoWest;
    }

    public void setStreetinfoWest(String streetinfoWest) {
        this.streetinfoWest = streetinfoWest;
    }

    public String getIdSouthwest() {
        return idSouthwest;
    }

    public void setIdSouthwest(String idSouthwest) {
        this.idSouthwest = idSouthwest;
    }

    public String getDirSouthwest() {
        return dirSouthwest;
    }

    public void setDirSouthwest(String dirSouthwest) {
        this.dirSouthwest = dirSouthwest;
    }

    public int getDirIntSouthwest() {
        return dirIntSouthwest;
    }

    public void setDirIntSouthwest(int dirIntSouthwest) {
        this.dirIntSouthwest = dirIntSouthwest;
    }

    public int getXingSouthwest() {
        return xingSouthwest;
    }

    public void setXingSouthwest(int xingSouthwest) {
        this.xingSouthwest = xingSouthwest;
    }

    public String getStreetinfoSouthwest() {
        return streetinfoSouthwest;
    }

    public void setStreetinfoSouthwest(String streetinfoSouthwest) {
        this.streetinfoSouthwest = streetinfoSouthwest;
    }

    public String getIdSouth() {
        return idSouth;
    }

    public void setIdSouth(String idSouth) {
        this.idSouth = idSouth;
    }

    public String getDirSouth() {
        return dirSouth;
    }

    public void setDirSouth(String dirSouth) {
        this.dirSouth = dirSouth;
    }

    public int getDirIntSouth() {
        return dirIntSouth;
    }

    public void setDirIntSouth(int dirIntSouth) {
        this.dirIntSouth = dirIntSouth;
    }

    public int getXingSouth() {
        return xingSouth;
    }

    public void setXingSouth(int xingSouth) {
        this.xingSouth = xingSouth;
    }

    public String getStreetinfoSouth() {
        return streetinfoSouth;
    }

    public void setStreetinfoSouth(String streetinfoSouth) {
        this.streetinfoSouth = streetinfoSouth;
    }

    public String getIdSoutheast() {
        return idSoutheast;
    }

    public void setIdSoutheast(String idSoutheast) {
        this.idSoutheast = idSoutheast;
    }

    public String getDirSoutheast() {
        return dirSoutheast;
    }

    public void setDirSoutheast(String dirSoutheast) {
        this.dirSoutheast = dirSoutheast;
    }

    public int getDirIntSoutheast() {
        return dirIntSoutheast;
    }

    public void setDirIntSoutheast(int dirIntSoutheast) {
        this.dirIntSoutheast = dirIntSoutheast;
    }

    public int getXingSoutheast() {
        return xingSoutheast;
    }

    public void setXingSoutheast(int xingSoutheast) {
        this.xingSoutheast = xingSoutheast;
    }

    public String getStreetinfoSoutheast() {
        return streetinfoSoutheast;
    }

    public void setStreetinfoSoutheast(String streetinfoSoutheast) {
        this.streetinfoSoutheast = streetinfoSoutheast;
    }

    public String getIdEast() {
        return idEast;
    }

    public void setIdEast(String idEast) {
        this.idEast = idEast;
    }

    public String getDirEast() {
        return dirEast;
    }

    public void setDirEast(String dirEast) {
        this.dirEast = dirEast;
    }

    public int getDirIntEast() {
        return dirIntEast;
    }

    public void setDirIntEast(int dirIntEast) {
        this.dirIntEast = dirIntEast;
    }

    public int getXingEast() {
        return xingEast;
    }

    public void setXingEast(int xingEast) {
        this.xingEast = xingEast;
    }

    public String getStreetinfoEast() {
        return streetinfoEast;
    }

    public void setStreetinfoEast(String streetinfoEast) {
        this.streetinfoEast = streetinfoEast;
    }

    public String getIdNortheast() {
        return idNortheast;
    }

    public void setIdNortheast(String idNortheast) {
        this.idNortheast = idNortheast;
    }

    public String getDirNortheast() {
        return dirNortheast;
    }

    public void setDirNortheast(String dirNortheast) {
        this.dirNortheast = dirNortheast;
    }

    public int getDirIntNortheast() {
        return dirIntNortheast;
    }

    public void setDirIntNortheast(int dirIntNortheast) {
        this.dirIntNortheast = dirIntNortheast;
    }

    public int getXingNortheast() {
        return xingNortheast;
    }

    public void setXingNortheast(int xingNortheast) {
        this.xingNortheast = xingNortheast;
    }

    public String getStreetinfoNortheast() {
        return streetinfoNortheast;
    }

    public void setStreetinfoNortheast(String streetinfoNortheast) {
        this.streetinfoNortheast = streetinfoNortheast;
    }

    @Override
    public String toString() {
        return "BLEPed [mac=" + mac + ", latitude=" + latitude + ", longitude=" + longitude + ", idNorth=" + idNorth
                + ", dirNorth=" + dirNorth + ", dirIntNorth=" + dirIntNorth + ", xingNorth=" + xingNorth
                + ", streetinfoNorth=" + streetinfoNorth + ", idEast=" + idEast + ", dirEast=" + dirEast
                + ", dirIntEast=" + dirIntEast + ", xingEast=" + xingEast + ", streetinfoEast=" + streetinfoEast
                + ", idSouth=" + idSouth + ", dirSouth=" + dirSouth + ", dirIntSouth=" + dirIntSouth + ", xingSouth="
                + xingSouth + ", streetinfoSouth=" + streetinfoSouth + ", idWest=" + idWest + ", dirWest=" + dirWest
                + ", dirIntWest=" + dirIntWest + ", xingWest=" + xingWest + ", streetinfoWest=" + streetinfoWest
                + ", idNortheast=" + idNortheast + ", dirNortheast=" + dirNortheast + ", dirIntNortheast="
                + dirIntNortheast + ", xingNortheast=" + xingNortheast + ", streetinfoNortheast=" + streetinfoNortheast
                + ", idSoutheast=" + idSoutheast + ", dirSoutheast=" + dirSoutheast + ", dirIntSoutheast="
                + dirIntSoutheast + ", xingSoutheast=" + xingSoutheast + ", streetinfoSoutheast=" + streetinfoSoutheast
                + ", idSouthwest=" + idSouthwest + ", dirSouthwest=" + dirSouthwest + ", dirIntSouthwest="
                + dirIntSouthwest + ", xingSouthwest=" + xingSouthwest + ", streetinfoSouthwest=" + streetinfoSouthwest
                + ", idNorthwest=" + idNorthwest + ", dirNorthwest=" + dirNorthwest + ", dirIntNorthwest="
                + dirIntNorthwest + ", xingNorthwest=" + xingNorthwest + ", streetinfoNorthwest=" + streetinfoNorthwest
                + ", desc=" + desc + ", bt_present=" + bt_present + "]";
    }

}
