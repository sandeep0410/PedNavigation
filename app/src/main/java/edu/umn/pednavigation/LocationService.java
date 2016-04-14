package edu.umn.pednavigation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import edu.umn.pednavigation.receiver.AlarmReceiver;
import edu.umn.pednavigation.settings.Settings;

/**
 * Created by Sandeep on 2/19/2016.
 */
public class LocationService extends Service {
    public static class NotificationConstants {
        public static final String START_SPEED_DETECTION_SERVICE = "Start Speed Detection Service";
        public static final String MAIN = "Main";
    }

    public static final double MAX_SPEED = 8.0;
    static final Double EARTH_RADIUS = 6371.00;
    public static float mSpeed = 0;
    public static double mLatitude = 0;
    public static double mLongitude = 0;
    public static double mAccuracy = 0;
    Thread t;
    private LocationManager locManager;
    private LocationListener locListener = new myLocationListener();
    private boolean gps_enabled = false;
    private boolean network_enabled = false;
    private Handler handler = new Handler();
    SharedPreferences mPrefs;
    private BLEScanner mScanner;
    public static double updateLat = -1;
    public static double updateLon = -1;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        Toast.makeText(getBaseContext(), "Service Started", Toast.LENGTH_SHORT).show();
        mPrefs = this.getSharedPreferences(
                "edu.umn.pednavigation", Context.MODE_PRIVATE);
        mSpeed = mPrefs.getFloat("Speed", mSpeed);
        mLatitude = mPrefs.getFloat("DatabaseUpdateLatitude", -1);
        mLongitude = mPrefs.getFloat("DatabaseUpdateLongitude", -1);
        final Runnable r = new Runnable() {
            public void run() {
                location();
            }
        };
        handler.postDelayed(r, 3000);
        storeSharedSettings();
        startDataBaseAlarm();
        return START_STICKY;
    }

    private void startDataBaseAlarm() {
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, 0);
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        long interval = 30 * 60 * 1000;
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);
        if (mPrefs != null)
            mPrefs = this.getSharedPreferences(
                    "edu.umn.pednavigation", Context.MODE_PRIVATE);
        updateLat = mPrefs.getFloat("DatabaseUpdateLatitude", -1);
        updateLon = mPrefs.getFloat("DatabaseUpdateLongitude", -1);
    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        Toast.makeText(getApplicationContext(), "Exiting Service", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor editor = getApplicationContext().getSharedPreferences(
                "edu.umn.pednavigation", Context.MODE_PRIVATE).edit();
        editor.putFloat("Speed", mSpeed);
    }

    public void location() {
        locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }
        try {
            network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }
        LogUtils.log("in on create.. 2");
        if (gps_enabled) {
            locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
            LogUtils.log("Enabled..");
        }
        if (network_enabled) {
            locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
            LogUtils.log("Disabled..");
        }
        LogUtils.log("in on create..3");
    }


    private void storeSharedSettings() {
        SharedPreferences prefs = this.getSharedPreferences(
                "edu.umn.pednavigation", Context.MODE_PRIVATE);
        Settings.vibration = prefs.getBoolean(Settings.VIBRATION, false);
        Settings.alarm = prefs.getBoolean(Settings.ALARM, true);
        Settings.data_collection = prefs.getBoolean(Settings.DATA_COLLECTION, true);
        Settings.display_alert = prefs.getBoolean(Settings.DISPLAY_ALERT, true);
        Settings.enable_calls = prefs.getBoolean(Settings.ENABLE_CALLS, false);
        Settings.rssi_value = prefs.getInt(Settings.RSSI_VALUE, 128);
        Settings.scan_Time = prefs.getInt(Settings.SCAN_TIME, 100);
        Settings.overspeed_block = prefs.getBoolean(Settings.OVERSPEED_BLOCK, false);
    }

    private class myLocationListener implements LocationListener {

        @Override
        public void onLocationChanged(Location location) {
            mLatitude = location.getLatitude();
            mLongitude = location.getLongitude();
            mAccuracy = location.getAccuracy();
            if (!location.hasSpeed() || location.getSpeed() == 0) {
                Location locationNET = ((LocationManager) getSystemService(Context.LOCATION_SERVICE)).getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (null != locationNET) {
                    if (locationNET.getSpeed() != mSpeed) {
                        mSpeed = locationNET.getSpeed();
                    }
                }
            } else {
                if (location.getSpeed() != mSpeed) {
                    mSpeed = location.getSpeed();
                }
            }

            if (location != null) {
                updateDBifRequired(location);
            }
            //LogUtils.log("Location Update received");
            //startScan
            if (NavigationActivity.getInstance() == null && location.getSpeed() < MAX_SPEED && !BLEScanner.getInstance(getApplicationContext()).isScanning())
                BLEScanner.getInstance(getApplicationContext()).scanLeDevice(true);
        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        private void updateDBifRequired(Location loc) {
            if (updateLat < 0)
                sendUpdateDBBroadCast(loc);
            else {
                Location savedLoc = new Location("Old Location");
                savedLoc.setLongitude(updateLon);
                savedLoc.setLatitude(updateLat);
                if (loc.distanceTo(savedLoc) > 50 * 1609)
                    sendUpdateDBBroadCast(loc);
            }
        }


    }

    private void sendUpdateDBBroadCast(Location loc) {
        if (Util.isOnline(this)) {
            Intent i = new Intent();
            i.setAction("edu.umn.pednavigation.DOWNLOAD.START");
            sendBroadcast(i);
            SharedPreferences.Editor editor = this.getSharedPreferences(
                    "edu.umn.pednavigation", Context.MODE_PRIVATE).edit();
            editor.putFloat("DatabaseUpdateLatitude", (float) loc.getLatitude());
            editor.putFloat("DatabaseUpdateLongitude", (float) loc.getLongitude());
            editor.apply();
            updateLat = loc.getLatitude();
            updateLon = loc.getLongitude();
        }
    }

}
