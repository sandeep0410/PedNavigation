package edu.umn.pednavigation.db;


import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import edu.umn.pednavigation.LocationService;
import edu.umn.pednavigation.LogUtils;
import edu.umn.pednavigation.dto.BLEFlag;
import edu.umn.pednavigation.dto.BLEPed;
import edu.umn.pednavigation.dto.BLETag;

/**
 * Created by Sandeep on 10/9/2015.
 */
public class DbDownloader extends Thread {
    private URL url;
    HttpURLConnection urlConnection;
    String table;
    private Context mContext;

    public DbDownloader(Context context, String table) {
        try {
            mContext = context;
            url = new URL("http://128.101.111.92:8080/Pedestrian/pedestrian");
            this.table = table;
        } catch (MalformedURLException e) {
            //e.printStackTrace();
        }
    }

    @Override
    public void run() {
        DBSQLiteHelper db = DBSQLiteHelper.getInstance(mContext);
        String data = downloadTable();
        if (data != null) {
            switch (table) {
                case DBUtils.BLEPN_TABLE:
                    List<BLEPed> blePeds = parsePed(data);
                    if (null != blePeds) {
                        db.deleteAll(table);
                        LogUtils.log("BTprinting after first delete");
                        printPedData(db);
                        for (BLEPed bt : blePeds) {
                            db.addBLEPedData(bt);
                        }
                        LogUtils.log("BTprinting after dataa addition");
                        printPedData(db);
                    }
                    break;
                case DBUtils.BLETAG_TABLE:
                    List<BLETag> bletags = parseBLE(data);
                    if (null != bletags) {
                        db.deleteAll(table);
                        LogUtils.log("BTprinting after first delete");
                        printBluetootheData(db);
                        for (BLETag bt : bletags) {
                            db.addBLETagData(bt);
                        }
                        LogUtils.log("BTprinting after dataa addition");
                        printBluetootheData(db);
                    }
                    break;
                case DBUtils.BLEFLAG_TABLE:
                    List<BLEFlag> bletype = parseType(data);
                    if (null != bletype) {
                        db.deleteAll(table);
                        LogUtils.log("BTprinting after first delete");
                        printFlagData(db);
                        for (BLEFlag bt : bletype) {
                            db.addBLEFlagData(bt);
                        }
                        LogUtils.log("BTprinting after dataa addition");
                        printFlagData(db);
                    }
                    break;
                default:
                    break;
            }
        }

    }

    private List<BLEPed> parsePed(String data) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<BLEPed>>() {
        }.getType();
        List<BLEPed> pojoList = gson.fromJson(data, listType);
        if (pojoList == null) {
            LogUtils.log("null list");
            return null;
        }
        return pojoList;
    }

    private void printFlagData(DBSQLiteHelper db) {
        List<BLEFlag> arr = db.getAllBLEFlagData();
        for (BLEFlag w : arr) {
            LogUtils.log(w.toString());
        }
    }

    private List<BLEFlag> parseType(String data) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<BLEFlag>>() {
        }.getType();
        List<BLEFlag> pojoList = gson.fromJson(data, listType);
        if (pojoList == null) {
            LogUtils.log("null list");
            return null;
        }
        return pojoList;
    }

    private void printPedData(DBSQLiteHelper db) {
        List<BLEPed> arr = db.getAllBLEPedData();
        for (BLEPed w : arr) {
            LogUtils.log(w.toString());
        }
    }

    private void printBluetootheData(DBSQLiteHelper db) {
        List<BLETag> arr = db.getAllBLEData();
        for (BLETag w : arr) {
            LogUtils.log(w.toString());
        }
    }


    public String downloadTable() {
        try {
            urlConnection = (HttpURLConnection) new URL(url.toString() + "?table=" + table + "&latitude=" + LocationService.updateLat
                    + "&longitude=" + LocationService.updateLon).openConnection();
            urlConnection.setConnectTimeout(5000);
            LogUtils.log(urlConnection.toString());
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            //********Reading Response From Server********
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                sb.append(line);
            }
            String data = sb.toString();
            return data;
            //********Reading Response From Server*********//

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != urlConnection)
                urlConnection.disconnect();
        }
        return null;
    }

    private List<BLETag> parseBLE(String data) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<BLETag>>() {
        }.getType();
        List<BLETag> pojoList = gson.fromJson(data, listType);
        if (pojoList == null) {
            LogUtils.log("null parsed");
            return null;
        }
        return pojoList;
    }

}
