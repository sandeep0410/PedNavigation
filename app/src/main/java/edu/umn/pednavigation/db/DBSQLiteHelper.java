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
                .append(DBUtils.BLEPN_ID1)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D1)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D11)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING1)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO1)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID2)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D2)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D22)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING2)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO2)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID3)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D3)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D33)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING3)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO3)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_ID4)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D4)
                .append(" TEXT, ")
                .append(DBUtils.BLEPN_D44)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_XING4)
                .append(" INTEGER, ")
                .append(DBUtils.BLEPN_STREETINFO4)
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
                .append(DBUtils.BLETAG_WZ_ID)
                .append(" INTEGER, ")
                .append(DBUtils.BLETAG_BLE_MAC)
                .append(" TEXT, ")
                .append(DBUtils.BLETAG_LATITUDE)
                .append(" DOUBLE, ")
                .append(DBUtils.BLETAG_LONGITUDE)
                .append(" DOUBLE, ")
                .append(DBUtils.BLETAG_SPEED_LIMIT)
                .append(" INTEGER, ")
                .append(DBUtils.BLETAG_MESSAGE)
                .append(" TEXT, ")
                .append(DBUtils.BLETAG_FLAG)
                .append(" INTEGER, ")
                .append(DBUtils.BLETAG_FILEPATH)
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
        values.put(DBUtils.BLETAG_WZ_ID, one.getWorkzoneID());
        values.put(DBUtils.BLETAG_BLE_MAC, one.getBleMac());
        values.put(DBUtils.BLETAG_LATITUDE, one.getLat());
        values.put(DBUtils.BLETAG_LONGITUDE, one.getLon());
        values.put(DBUtils.BLETAG_SPEED_LIMIT, one.getSpeedLimit());
        values.put(DBUtils.BLETAG_MESSAGE, one.getMessage());
        values.put(DBUtils.BLETAG_FLAG, one.getFlag());
        values.put(DBUtils.BLETAG_FILEPATH, one.getFileName());
        db.insert(DBUtils.BLETAG_TABLE, null, values);

    }


    public List<BLETag> getAllBLEData() {
        List<BLETag> bleTags = new ArrayList<BLETag>();
        String selectQuery = "SELECT *FROM " + DBUtils.BLETAG_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                BLETag bt = new BLETag(-1, "", -1, -1, -1, "", -1, "");
                bt.setWorkzoneID(Integer.parseInt(cursor.getString(1)));
                bt.setBleMac(cursor.getString(2));
                bt.setLat(Double.parseDouble(cursor.getString(3)));
                bt.setLon(Double.parseDouble(cursor.getString(4)));
                bt.setSpeedLimit(Integer.parseInt(cursor.getString(5)));
                bt.setMessage(cursor.getString(6));
                bt.setFlag(Integer.parseInt(cursor.getString(7)));
                bt.setFileName(cursor.getString(8));
                bleTags.add(bt);
            } while (cursor.moveToNext());
        }
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
                bt = new BLETag(-1, "", -1, -1, -1, "", -1, "");
                bt.setWorkzoneID(Integer.parseInt(cursor.getString(1)));
                bt.setBleMac(cursor.getString(2));
                bt.setLat(Double.parseDouble(cursor.getString(3)));
                bt.setLon(Double.parseDouble(cursor.getString(4)));
                bt.setSpeedLimit(Integer.parseInt(cursor.getString(5)));
                bt.setMessage(cursor.getString(6));
                bt.setFlag(Integer.parseInt(cursor.getString(7)));
                bt.setFileName(cursor.getString(8));
            } while (cursor.moveToNext());
        }
        LogUtils.log("printing after query: " + bt);
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
                bt.setFlag(Integer.parseInt(cursor.getString(2)));
            } while (cursor.moveToNext());
        }
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
        values.put(DBUtils.BLEPN_ID1, ped.getId1());
        values.put(DBUtils.BLEPN_D1, ped.getD1());
        values.put(DBUtils.BLEPN_D11, ped.getD11());
        values.put(DBUtils.BLEPN_XING1, ped.getXing1());
        values.put(DBUtils.BLEPN_STREETINFO1, ped.getStreetinfo1());
        values.put(DBUtils.BLEPN_ID2, ped.getId2());
        values.put(DBUtils.BLEPN_D2, ped.getD2());
        values.put(DBUtils.BLEPN_D22, ped.getD22());
        values.put(DBUtils.BLEPN_XING2, ped.getXing2());
        values.put(DBUtils.BLEPN_STREETINFO2, ped.getStreetinfo2());
        values.put(DBUtils.BLEPN_ID3, ped.getId3());
        values.put(DBUtils.BLEPN_D3, ped.getD3());
        values.put(DBUtils.BLEPN_D33, ped.getD33());
        values.put(DBUtils.BLEPN_XING3, ped.getXing3());
        values.put(DBUtils.BLEPN_STREETINFO3, ped.getStreetinfo3());
        values.put(DBUtils.BLEPN_ID4, ped.getId4());
        values.put(DBUtils.BLEPN_D4, ped.getD4());
        values.put(DBUtils.BLEPN_D44, ped.getD44());
        values.put(DBUtils.BLEPN_XING4, ped.getXing4());
        values.put(DBUtils.BLEPN_STREETINFO4, ped.getStreetinfo4());
        values.put(DBUtils.BLEPN_DESC, ped.getDesc());
        values.put(DBUtils.BLEPN_BT_PRESENT , ped.getBt_present());
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
                bt.setId1(cursor.getString(4));
                bt.setD1(cursor.getString(5));
                bt.setD11(Integer.parseInt(cursor.getString(6)));
                bt.setXing1(Integer.parseInt(cursor.getString(7)));
                bt.setStreetinfo1(cursor.getString(8));
                bt.setId2(cursor.getString(9));
                bt.setD2(cursor.getString(10));
                bt.setD22(Integer.parseInt(cursor.getString(11)));
                bt.setXing2(Integer.parseInt(cursor.getString(12)));
                bt.setStreetinfo2(cursor.getString(13));
                bt.setId3(cursor.getString(14));
                bt.setD3(cursor.getString(15));
                bt.setD33(Integer.parseInt(cursor.getString(16)));
                bt.setXing3(Integer.parseInt(cursor.getString(17)));
                bt.setStreetinfo3(cursor.getString(18));
                bt.setId4(cursor.getString(19));
                bt.setD4(cursor.getString(20));
                bt.setD44(Integer.parseInt(cursor.getString(21)));
                bt.setXing4(Integer.parseInt(cursor.getString(22)));
                bt.setStreetinfo4(cursor.getString(23));
                bt.setDesc(cursor.getString(24));
                bt.setBt_present(Integer.parseInt(cursor.getString(25)));
                blePeds.add(bt);
            } while (cursor.moveToNext());
        }
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
                bt.setId1(cursor.getString(4));
                bt.setD1(cursor.getString(5));
                bt.setD11(Integer.parseInt(cursor.getString(6)));
                bt.setXing1(Integer.parseInt(cursor.getString(7)));
                bt.setStreetinfo1(cursor.getString(8));
                bt.setId2(cursor.getString(9));
                bt.setD2(cursor.getString(10));
                bt.setD22(Integer.parseInt(cursor.getString(11)));
                bt.setXing2(Integer.parseInt(cursor.getString(12)));
                bt.setStreetinfo2(cursor.getString(13));
                bt.setId3(cursor.getString(14));
                bt.setD3(cursor.getString(15));
                bt.setD33(Integer.parseInt(cursor.getString(16)));
                bt.setXing3(Integer.parseInt(cursor.getString(17)));
                bt.setStreetinfo3(cursor.getString(18));
                bt.setId4(cursor.getString(19));
                bt.setD4(cursor.getString(20));
                bt.setD44(Integer.parseInt(cursor.getString(21)));
                bt.setXing4(Integer.parseInt(cursor.getString(22)));
                bt.setStreetinfo4(cursor.getString(23));
                bt.setDesc(cursor.getString(24));
                bt.setBt_present(Integer.parseInt(cursor.getString(25)));
            } while (cursor.moveToNext());
        }
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
        return blePhases;
    }


    public BLEPhase getBlePhase(String address, String dir) {
        String query = "SELECT * FROM " + DBUtils.BLEPHASE_TABLE + " WHERE " + DBUtils.BLEPHASE_MAC + " LIKE '%" + address + "%'" +" AND "+DBUtils.BLEPHASE_DIR + " LIKE '%" + dir + "%'";
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
        LogUtils.log("printing after query: " + bt);
        return bt;
    }
}
