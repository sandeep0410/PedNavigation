package edu.umn.pednavigation.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import edu.umn.pednavigation.LogUtils;
import edu.umn.pednavigation.dto.BLEFlag;
import edu.umn.pednavigation.dto.BLEPed;
import edu.umn.pednavigation.dto.BLEPhase;
import edu.umn.pednavigation.dto.BLETag;

/**
 * Created by Sandeep on 10/3/2015.
 */
public class DBSQLiteHelper extends SQLiteOpenHelper {
    private static final String PED_NAVIGATION = "PED_NAVIGATION";
    private static final int DB_VERSION = 1;
    private static final String KEY_ID = "id";
    private static DBSQLiteHelper mInstance = null;


    private DBSQLiteHelper(Context context) {
        super(context, PED_NAVIGATION, null, DB_VERSION);
    }

    public static DBSQLiteHelper getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DBSQLiteHelper(context.getApplicationContext());
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //create Table for WorkZones
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ").append(DBUtils.BLEFLAG_TABLE)
                .append("(")
                .append(KEY_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DBUtils.BLEFLAG_BLE_MAC)
                .append(" TEXT, ")
                .append(DBUtils.BLEFLAG_FLAG)
                .append(" INTEGER);");
        db.execSQL(sb.toString());
        sb.setLength(0);

        sb.append("CREATE TABLE ").append(DBUtils.BLEPN_TABLE)
                .append("(")
                .append(KEY_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DBUtils.BLEPN_MAC)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_LATITUE)
                .append(" DOUBLE, ")
                .append(DBUtils.BLEPN_LONGITUDE)
                .append(" DOUBLE, ")
                .append(DBUtils.BLEPN_IDNORTH)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_NORTH)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_INT_NORTH)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING_NORTH)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO_NORTH)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID_EAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_EAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_INT_EAST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING_EAST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO_EAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID_SOUTH)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_SOUTH)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_INT_SOUTH)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING_SOUTH)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO_SOUTH)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID_WEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_WEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_INT_WEST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING_WEST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO_WEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID_NORTHEAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_NORTHEAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_INT_NORTHEAST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING_NORTHEAST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO_NORTHEAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID_SOUTHEAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_SOUTHEAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_INT_SOUTHEAST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING_SOUTHEAST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO_SOUTHEAST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID_SOUTHWEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_SOUTHWEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_INT_SOUTHWEST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING_SOUTHWEST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO_SOUTHWEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID_NORTHWEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_NORTHWEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D_INT_NORTHWEST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING_NORTHWEST)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO_NORTHWEST)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_DESC)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_BT_PRESENT)
                .append(" INTEGER);");
        db.execSQL(sb.toString());
        sb.setLength(0);
        //create Table for BLE Tags
        sb.append("CREATE TABLE ")
                .append(DBUtils.BLETAG_TABLE)
                .append(" (")
                .append(KEY_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DBUtils.BLETAG_BLE_MAC)
                .append(" TEXT, ")
                .append(DBUtils.BLETAG_LATITUDE)
                .append(" DOUBLE, ")
                .append(DBUtils.BLETAG_LONGITUDE)
                .append(" DOUBLE, ")
                .append(DBUtils.BLETAG_STREETINFO1)
                .append(" TEXT, ")
                .append(DBUtils.BLETAG_STREETINFO2)
                .append(" TEXT, ")
                .append(DBUtils.BLETAG_STREETINFO31)
                .append(" TEXT, ")
                .append(DBUtils.BLETAG_STREETINFO32)
                .append(" TEXT, ")
                .append(DBUtils.BLETAG_STREETINFO4)
                .append(" TEXT);");
        db.execSQL(sb.toString());
        sb.setLength(0);
        //create Table for BLE Phase
        sb.append("CREATE TABLE ")
                .append(DBUtils.BLEPHASE_TABLE)
                .append(" (")
                .append(KEY_ID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(DBUtils.BLEPHASE_INTX_ID)
                .append(" TEXT, ")
                .append(DBUtils.BLEPHASE_MAC)
                .append(" TEXT, ")
                .append(DBUtils.BLEPHASE_DIR)
                .append(" TEXT, ")
                .append(DBUtils.BLEPHASE_PHASE)
                .append(" INTEGER);");
        db.execSQL(sb.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void addBLETagData(BLETag one) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtils.BLETAG_BLE_MAC, one.getBleMac());
        values.put(DBUtils.BLETAG_LATITUDE, one.getLat());
        values.put(DBUtils.BLETAG_LONGITUDE, one.getLon());
        values.put(DBUtils.BLETAG_STREETINFO1, one.getStreetinfo1());
        values.put(DBUtils.BLETAG_STREETINFO2, one.getStreetinfo2());
        values.put(DBUtils.BLETAG_STREETINFO31, one.getStreetinfo31());
        values.put(DBUtils.BLETAG_STREETINFO32, one.getStreetinfo32());
        values.put(DBUtils.BLETAG_STREETINFO4, one.getStreetinfo4());
        db.insert(DBUtils.BLETAG_TABLE, null, values);

    }


    public List<BLETag> getAllBLEData() {
        List<BLETag> bleTags = new ArrayList<BLETag>();
        String selectQuery = "SELECT *FROM " + DBUtils.BLETAG_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BLETag bt = new BLETag();
                bt.setBleMac(cursor.getString(1));
                bt.setLat(Double.parseDouble(cursor.getString(2)));
                bt.setLon(Double.parseDouble(cursor.getString(3)));
                bt.setStreetinfo1(cursor.getString(4));
                bt.setStreetinfo2(cursor.getString(5));
                bt.setStreetinfo31(cursor.getString(6));
                bt.setStreetinfo32(cursor.getString(7));
                bt.setStreetinfo4(cursor.getString(8));
                bleTags.add(bt);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        return bleTags;
    }

    public void deleteAll(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + table);
    }

    public BLETag getBleTag(String address) {
        String query = "SELECT * FROM " + DBUtils.BLETAG_TABLE + " WHERE " + DBUtils.BLETAG_BLE_MAC + " LIKE '%" + address + "%'";
        LogUtils.log("printing query: " + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BLETag bt = null;

        if (cursor.moveToFirst()) {
            do {
                bt = new BLETag();
                bt.setBleMac(cursor.getString(1));
                bt.setLat(Double.parseDouble(cursor.getString(2)));
                bt.setLon(Double.parseDouble(cursor.getString(3)));
                bt.setStreetinfo1(cursor.getString(4));
                bt.setStreetinfo2(cursor.getString(5));
                bt.setStreetinfo31(cursor.getString(6));
                bt.setStreetinfo32(cursor.getString(7));
                bt.setStreetinfo4(cursor.getString(8));
            } while (cursor.moveToNext());
        }
        LogUtils.log("printing after query: " + bt);
        if (cursor != null)
            cursor.close();
        return bt;
    }

    /////////////////////////////////////////////////////////////////////////
    public void addBLEFlagData(BLEFlag flag) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtils.BLEFLAG_BLE_MAC, flag.getMac());
        values.put(DBUtils.BLEFLAG_FLAG, flag.getFlag());
        db.insert(DBUtils.BLEFLAG_TABLE, null, values);

    }


    public List<BLEFlag> getAllBLEFlagData() {
        List<BLEFlag> bleFlags = new ArrayList<BLEFlag>();
        String selectQuery = "SELECT *FROM " + DBUtils.BLEFLAG_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BLEFlag bt = new BLEFlag("", -1);
                bt.setMac(cursor.getString(1));
                bt.setFlag(Integer.parseInt(cursor.getString(2)));
                bleFlags.add(bt);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        return bleFlags;
    }


    public BLEFlag getBleFlag(String address) {
        String query = "SELECT * FROM " + DBUtils.BLEFLAG_TABLE + " WHERE " + DBUtils.BLEFLAG_BLE_MAC + " LIKE '%" + address + "%'";
        LogUtils.log("printing query: " + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BLEFlag bt = null;

        if (cursor.moveToFirst()) {
            do {
                bt = new BLEFlag("", -1);
                bt.setMac(cursor.getString(1));
                bt.setFlag(cursor.getInt(2));
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        LogUtils.log("printing after query: " + bt);
        return bt;
    }

    //////////////////////////////////////////////////////////////////////////////
    public void addBLEPedData(BLEPed ped) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtils.BLEPN_MAC, ped.getMac());
        values.put(DBUtils.BLEPN_LATITUE, ped.getLatitude());
        values.put(DBUtils.BLEPN_LONGITUDE, ped.getLongitude());
        values.put(DBUtils.BLEPN_IDNORTH, ped.getIdNorth());
        values.put(DBUtils.BLEPN_D_NORTH, ped.getDirNorth());
        values.put(DBUtils.BLEPN_D_INT_NORTH, ped.getDirIntNorth());
        values.put(DBUtils.BLEPN_XING_NORTH, ped.getXingNorth());
        values.put(DBUtils.BLEPN_STREETINFO_NORTH, ped.getStreetinfoNorth());

        values.put(DBUtils.BLEPN_ID_EAST, ped.getIdEast());
        values.put(DBUtils.BLEPN_D_EAST, ped.getDirEast());
        values.put(DBUtils.BLEPN_D_INT_EAST, ped.getDirIntEast());
        values.put(DBUtils.BLEPN_XING_EAST, ped.getXingEast());
        values.put(DBUtils.BLEPN_STREETINFO_EAST, ped.getStreetinfoEast());

        values.put(DBUtils.BLEPN_ID_SOUTH, ped.getIdSouth());
        values.put(DBUtils.BLEPN_D_SOUTH, ped.getDirSouth());
        values.put(DBUtils.BLEPN_D_INT_SOUTH, ped.getDirIntSouth());
        values.put(DBUtils.BLEPN_XING_SOUTH, ped.getXingSouth());
        values.put(DBUtils.BLEPN_STREETINFO_SOUTH, ped.getStreetinfoSouth());

        values.put(DBUtils.BLEPN_ID_WEST, ped.getIdWest());
        values.put(DBUtils.BLEPN_D_WEST, ped.getDirWest());
        values.put(DBUtils.BLEPN_D_INT_WEST, ped.getDirIntWest());
        values.put(DBUtils.BLEPN_XING_WEST, ped.getXingWest());
        values.put(DBUtils.BLEPN_STREETINFO_WEST, ped.getStreetinfoWest());

        values.put(DBUtils.BLEPN_ID_NORTHEAST, ped.getIdNortheast());
        values.put(DBUtils.BLEPN_D_NORTHEAST, ped.getDirNortheast());
        values.put(DBUtils.BLEPN_D_INT_NORTHEAST, ped.getDirIntNortheast());
        values.put(DBUtils.BLEPN_XING_NORTHEAST, ped.getXingNortheast());
        values.put(DBUtils.BLEPN_STREETINFO_NORTHEAST, ped.getStreetinfoNortheast());

        values.put(DBUtils.BLEPN_ID_SOUTHEAST, ped.getIdSoutheast());
        values.put(DBUtils.BLEPN_D_SOUTHEAST, ped.getDirSoutheast());
        values.put(DBUtils.BLEPN_D_INT_SOUTHEAST, ped.getDirIntSoutheast());
        values.put(DBUtils.BLEPN_XING_SOUTHEAST, ped.getXingSoutheast());
        values.put(DBUtils.BLEPN_STREETINFO_SOUTHEAST, ped.getStreetinfoSoutheast());

        values.put(DBUtils.BLEPN_ID_SOUTHWEST, ped.getIdSouthwest());
        values.put(DBUtils.BLEPN_D_SOUTHWEST, ped.getDirSouthwest());
        values.put(DBUtils.BLEPN_D_INT_SOUTHWEST, ped.getDirIntSouthwest());
        values.put(DBUtils.BLEPN_XING_SOUTHWEST, ped.getXingSouthwest());
        values.put(DBUtils.BLEPN_STREETINFO_SOUTHWEST, ped.getStreetinfoSouthwest());

        values.put(DBUtils.BLEPN_ID_NORTHWEST, ped.getIdNorthwest());
        values.put(DBUtils.BLEPN_D_NORTHWEST, ped.getDirNorthwest());
        values.put(DBUtils.BLEPN_D_INT_NORTHWEST, ped.getDirIntNorthwest());
        values.put(DBUtils.BLEPN_XING_NORTHWEST, ped.getXingNorthwest());
        values.put(DBUtils.BLEPN_STREETINFO_NORTHWEST, ped.getStreetinfoNorthwest());

        values.put(DBUtils.BLEPN_DESC, ped.getDesc());
        values.put(DBUtils.BLEPN_BT_PRESENT, ped.getBt_present());
        db.insert(DBUtils.BLEPN_TABLE, null, values);

    }


    public List<BLEPed> getAllBLEPedData() {
        List<BLEPed> blePeds = new ArrayList<BLEPed>();
        String selectQuery = "SELECT *FROM " + DBUtils.BLEPN_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BLEPed bt = new BLEPed();
                bt.setMac(cursor.getString(1));
                bt.setLatitude(Double.parseDouble(cursor.getString(2)));
                bt.setLongitude(Double.parseDouble(cursor.getString(3)));
                bt.setIdNorth(cursor.getString(4));
                bt.setDirNorth(cursor.getString(5));
                bt.setDirIntNorth(Integer.parseInt(cursor.getString(6)));
                bt.setXingNorth(Integer.parseInt(cursor.getString(7)));
                bt.setStreetinfoNorth(cursor.getString(8));

                bt.setIdEast(cursor.getString(9));
                bt.setDirEast(cursor.getString(10));
                bt.setDirIntEast(Integer.parseInt(cursor.getString(11)));
                bt.setXingEast(Integer.parseInt(cursor.getString(12)));
                bt.setStreetinfoEast(cursor.getString(13));

                bt.setIdSouth(cursor.getString(14));
                bt.setDirSouth(cursor.getString(15));
                bt.setDirIntSouth(Integer.parseInt(cursor.getString(16)));
                bt.setXingSouth(Integer.parseInt(cursor.getString(17)));
                bt.setStreetinfoSouth(cursor.getString(18));

                bt.setIdWest(cursor.getString(19));
                bt.setDirWest(cursor.getString(20));
                bt.setDirIntWest(Integer.parseInt(cursor.getString(21)));
                bt.setXingWest(Integer.parseInt(cursor.getString(22)));
                bt.setStreetinfoWest(cursor.getString(23));

                bt.setIdNortheast(cursor.getString(24));
                bt.setDirNortheast(cursor.getString(25));
                bt.setDirIntNortheast(Integer.parseInt(cursor.getString(26)));
                bt.setXingNortheast(Integer.parseInt(cursor.getString(27)));
                bt.setStreetinfoNortheast(cursor.getString(28));

                bt.setIdSoutheast(cursor.getString(29));
                bt.setDirSoutheast(cursor.getString(30));
                bt.setDirIntSoutheast(Integer.parseInt(cursor.getString(31)));
                bt.setXingSoutheast(Integer.parseInt(cursor.getString(32)));
                bt.setStreetinfoSoutheast(cursor.getString(33));

                bt.setIdSouthwest(cursor.getString(34));
                bt.setDirSouthwest(cursor.getString(35));
                bt.setDirIntSouthwest(Integer.parseInt(cursor.getString(36)));
                bt.setXingSouthwest(Integer.parseInt(cursor.getString(37)));
                bt.setStreetinfoSouthwest(cursor.getString(38));

                bt.setIdNorthwest(cursor.getString(39));
                bt.setDirNorthwest(cursor.getString(40));
                bt.setDirIntNorthwest(Integer.parseInt(cursor.getString(41)));
                bt.setXingNorthwest(Integer.parseInt(cursor.getString(42)));
                bt.setStreetinfoNorthwest(cursor.getString(43));

                bt.setDesc(cursor.getString(44));
                bt.setBt_present(Integer.parseInt(cursor.getString(45)));
                blePeds.add(bt);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        return blePeds;
    }


    public BLEPed getBlePed(String address) {
        String query = "SELECT * FROM " + DBUtils.BLEPN_TABLE + " WHERE " + DBUtils.BLEPN_MAC + " LIKE '%" + address + "%'";
        LogUtils.log("printing query: " + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BLEPed bt = null;

        if (cursor.moveToFirst()) {
            do {
                bt = new BLEPed();
                bt.setMac(cursor.getString(1));
                bt.setLatitude(Double.parseDouble(cursor.getString(2)));
                bt.setLongitude(Double.parseDouble(cursor.getString(3)));
                bt.setIdNorth(cursor.getString(4));
                bt.setDirNorth(cursor.getString(5));
                bt.setDirIntNorth(Integer.parseInt(cursor.getString(6)));
                bt.setXingNorth(Integer.parseInt(cursor.getString(7)));
                bt.setStreetinfoNorth(cursor.getString(8));

                bt.setIdEast(cursor.getString(9));
                bt.setDirEast(cursor.getString(10));
                bt.setDirIntEast(Integer.parseInt(cursor.getString(11)));
                bt.setXingEast(Integer.parseInt(cursor.getString(12)));
                bt.setStreetinfoEast(cursor.getString(13));

                bt.setIdSouth(cursor.getString(14));
                bt.setDirSouth(cursor.getString(15));
                bt.setDirIntSouth(Integer.parseInt(cursor.getString(16)));
                bt.setXingSouth(Integer.parseInt(cursor.getString(17)));
                bt.setStreetinfoSouth(cursor.getString(18));

                bt.setIdWest(cursor.getString(19));
                bt.setDirWest(cursor.getString(20));
                bt.setDirIntWest(Integer.parseInt(cursor.getString(21)));
                bt.setXingWest(Integer.parseInt(cursor.getString(22)));
                bt.setStreetinfoWest(cursor.getString(23));

                bt.setIdNortheast(cursor.getString(24));
                bt.setDirNortheast(cursor.getString(25));
                bt.setDirIntNortheast(Integer.parseInt(cursor.getString(26)));
                bt.setXingNortheast(Integer.parseInt(cursor.getString(27)));
                bt.setStreetinfoNortheast(cursor.getString(28));

                bt.setIdSoutheast(cursor.getString(29));
                bt.setDirSoutheast(cursor.getString(30));
                bt.setDirIntSoutheast(Integer.parseInt(cursor.getString(31)));
                bt.setXingSoutheast(Integer.parseInt(cursor.getString(32)));
                bt.setStreetinfoSoutheast(cursor.getString(33));

                bt.setIdSouthwest(cursor.getString(34));
                bt.setDirSouthwest(cursor.getString(35));
                bt.setDirIntSouthwest(Integer.parseInt(cursor.getString(36)));
                bt.setXingSouthwest(Integer.parseInt(cursor.getString(37)));
                bt.setStreetinfoSouthwest(cursor.getString(38));

                bt.setIdNorthwest(cursor.getString(39));
                bt.setDirNorthwest(cursor.getString(40));
                bt.setDirIntNorthwest(Integer.parseInt(cursor.getString(41)));
                bt.setXingNorthwest(Integer.parseInt(cursor.getString(42)));
                bt.setStreetinfoNorthwest(cursor.getString(43));

                bt.setDesc(cursor.getString(44));
                bt.setBt_present(Integer.parseInt(cursor.getString(45)));
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        LogUtils.log("printing after query: " + bt);
        return bt;
    }

    ////////////////////////BLE Phase Data Functions/////////////////////
    public void addBLEPhaseData(BLEPhase phase) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBUtils.BLEPHASE_INTX_ID, phase.getIntx_id());
        values.put(DBUtils.BLEPHASE_MAC, phase.getMac());
        values.put(DBUtils.BLEPHASE_DIR, phase.getDir());
        values.put(DBUtils.BLEPHASE_PHASE, phase.getPhase());
        db.insert(DBUtils.BLEPHASE_TABLE, null, values);
    }


    public List<BLEPhase> getAllBLEPhaseData() {
        List<BLEPhase> blePhases = new ArrayList<BLEPhase>();
        String selectQuery = "SELECT *FROM " + DBUtils.BLEPHASE_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BLEPhase bt = new BLEPhase();
                bt.setIntx_id(cursor.getString(1));
                bt.setMac(cursor.getString(2));
                bt.setDir(cursor.getString(3));
                bt.setPhase(cursor.getInt(4));
                blePhases.add(bt);
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        return blePhases;
    }


    public BLEPhase getBlePhase(String address, String dir) {
        String query = "SELECT * FROM " + DBUtils.BLEPHASE_TABLE + " WHERE " + DBUtils.BLEPHASE_MAC + " LIKE '%" + address + "%'" + " AND " + DBUtils.BLEPHASE_DIR + " LIKE '%" + dir + "%'";
        LogUtils.log("printing query: " + query);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        BLEPhase bt = null;

        if (cursor.moveToFirst()) {
            do {
                bt = new BLEPhase();
                bt.setIntx_id(cursor.getString(1));
                bt.setMac(cursor.getString(2));
                bt.setDir(cursor.getString(3));
                bt.setPhase(cursor.getInt(4));
            } while (cursor.moveToNext());
        }
        if (cursor != null)
            cursor.close();
        LogUtils.log("printing after query: " + bt);
        return bt;
    }
}
