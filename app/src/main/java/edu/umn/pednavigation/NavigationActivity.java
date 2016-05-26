/*
 * PedestrianNavigation.java
 * Pedestrian Navigation main program.
 * Created on Friday, May 28, 2010, 10:31:08 AM
 */

/*
 * PI: Chen-Fu Liao
 * University of Minnesota
 * Department of Civil Engineering
 * Minnesota Traffic Observatory (MTO)
 * 500 Pillsbury Drive SE
 * Minneapolis, MN 55455
 * 
 * Students: Avanish Rayankula, Sowmya Ramesh & Niveditha Baskar
 */

package edu.umn.pednavigation;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import edu.umn.pednavigation.db.DBSQLiteHelper;
import edu.umn.pednavigation.db.DBUtils;
import edu.umn.pednavigation.dto.BLEPed;
import edu.umn.pednavigation.dto.BLEPhase;
import edu.umn.pednavigation.dto.BLETag;

public class NavigationActivity extends Activity implements SensorEventListener, GestureDetector.OnGestureListener,
        GestureDetector.OnDoubleTapListener {
    final String[] directions = {"North", "East", "South", "West", "Northeast", "Southeast", "Southwest", "Northwest"};
    private int SCREEN_TIME_PERIOD = 60;
    int flag = -1;
    String address = null;
    TextView timer;
    TextView direction;
    RelativeLayout parentlayout;
    int stopTime = SCREEN_TIME_PERIOD;
    private static NavigationActivity _instance;
    SensorManager sm;
    Sensor accelerometer;
    Sensor magnetometer;
    double dirDeg = 0;
    private AudioManager audiomanager;
    private TextToSpeech tts;
    float[] mGravity;
    float[] mGeomagnetic;
    private GestureDetector mDetector;
    private int mTimeToWalk = -1;
    String intx_id;
    Map<String, Integer> directionPhase = new HashMap<>();
    Map<String, Integer> directionTimer = new HashMap<>();
    Timer timerThread;
    Handler timerHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                timer.setText(String.valueOf(stopTime));
            else {
                if (timerThread != null) {
                    timerThread.cancel();
                    timerThread.purge();
                }
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.log("Oncreate");
        if (!isMyServiceRunning(LocationService.class)) {

            (new Thread(new Runnable() {
                @Override
                public void run() {
                    Intent i = new Intent(getApplicationContext(), LocationService.class);
                    startService(i);
                }
            })).start();
        }
        setContentView(R.layout.navigation_main);
        TextView mode = (TextView) findViewById(R.id.mode);
        timer = (TextView) findViewById(R.id.timer);
        direction = (TextView) findViewById(R.id.direction);
        parentlayout = (RelativeLayout) findViewById(R.id.layout);
        timer.setText(String.valueOf(stopTime));
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", -1);
        address = intent.getStringExtra("bleAddress");
        if (flag == DBUtils.INTX) {
            initializeMaps();
        }
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
        mDetector = new GestureDetector(this, this);
        // Set the gesture detector as the double tap
        // listener.
        mDetector.setOnDoubleTapListener(this);
        _instance = this;
        initializeAudioItems();
        timerThread = new Timer();
        timerThread.schedule(new MyTimerTask(), 0, 1000);
    }

    private void initializeMaps() {
        directionPhase.clear();
        directionTimer.clear();
        DBSQLiteHelper db = DBSQLiteHelper.getInstance(this);
        BLEPhase phaseObject;
        for (String dir : directions) {
            phaseObject = db.getBlePhase(address, dir);
            if (phaseObject != null) {
                directionPhase.put(dir, phaseObject.getPhase());
                if (intx_id == null)
                    intx_id = phaseObject.getIntx_id();
            }

        }
        for (String dir : directions) {
            directionTimer.put(dir, -1);
        }

        LogUtils.log("Phase Objects");
        for(Map.Entry entry :directionPhase.entrySet()){
            LogUtils.log(entry.getKey() + " " + entry.getValue());
        }


        LogUtils.log("Direction Objects");
        for(Map.Entry entry :directionPhase.entrySet()){
            LogUtils.log(entry.getKey()+" " +entry.getValue());
        }

    }

    private void initializeAudioItems() {
        audiomanager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {

            @Override
            public void onInit(int status) {

            }
        });

        tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
            @Override
            public void onStart(String s) {
                int curVolume = Integer.parseInt(s);
                LogUtils.log("Utterance started: " + curVolume);
                if (curVolume < (.5 * audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)))

                {
                    audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, (int) (.5 * audiomanager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)), 0);
                }
                LogUtils.log("after starting: " + audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC));
            }

            @Override
            public void onDone(String s) {
                LogUtils.log("after completion: " + audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC));
                int curVolume = Integer.parseInt(s);
                LogUtils.log("Utterance completed: " + curVolume);
                audiomanager.setStreamVolume(AudioManager.STREAM_MUSIC, curVolume, 0);
            }

            @Override
            public void onError(String s) {

            }
        });
    }

    private void speak(String msg) {
        if (!tts.isSpeaking()) {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, "" + audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    }

    private void performDoubleClick() {
        if (flag == DBUtils.CONS) {
            DBSQLiteHelper db = DBSQLiteHelper.getInstance(this);
            BLETag ble = db.getBleTag(address);
            String msg = "Attention " +ble.getStreetinfo1() +" pedestrians. You are at the "+ble.getStreetinfo2()
                    +". "+ble.getStreetinfo31() +" for " +ble.getStreetinfo32()+". "+ble.getStreetinfo4();
            speak(msg);
        } else if (flag == DBUtils.INTX) {
            String curDir = getCurrentDirection();
            DBSQLiteHelper db = DBSQLiteHelper.getInstance(this);
            BLEPed ped = db.getBlePed(address);
            String message = null;
            if (ped != null)
                message = getInfoMessage(curDir, ped);

            for (Map.Entry entry : directionTimer.entrySet())
                LogUtils.log("Double click: " + entry.getKey() + ": " + entry.getValue());
            if (directionTimer.containsKey(curDir) && message !=null) {
                int time = directionTimer.get(curDir);
                if(directionPhase.containsKey(curDir) && directionPhase.get(curDir) == -1) {
                    speak(message);
                }else if (time < 0)
                    speak(message + " Please wait for walk signal.");
                else
                    speak(message + " Walk signal is on. " + time + " seconds left. ");
            } else
                speak("No information. Please turn for data.");
        }
        // LogUtils.log("Single Tap Confirmed " + stopTime);
        stopTime = SCREEN_TIME_PERIOD;
    }

    private String getCurrentDirection() {
        return Util.getDir((int) dirDeg);
    }

    private void performSingleClick() {
        if (flag == DBUtils.CONS) {
            DBSQLiteHelper db = DBSQLiteHelper.getInstance(this);
            BLETag ble = db.getBleTag(address);
            String msg = "Attention " +ble.getStreetinfo1() +" pedestrians. You are at the "+ble.getStreetinfo2()
                    +". "+ble.getStreetinfo31() +" for " +ble.getStreetinfo32()+". "+ble.getStreetinfo4();
            speak(msg);
        } else if (flag == DBUtils.INTX) {
            DBSQLiteHelper db = DBSQLiteHelper.getInstance(this);
            BLEPed ped = db.getBlePed(address);
            if (ped != null) {
                String dir = getCurrentDirection();
                String message = getInfoMessage(dir, ped);

                if (message != null)
                    message = "You are pointing to " + dir + ", " + message;
                else
                    message = "No information. Please turn for data.";
                speak(message);
            }
        }
        // LogUtils.log("Long Tap Confirmed " + stopTime);
        stopTime = SCREEN_TIME_PERIOD;
    }

    private String getInfoMessage(String dir, BLEPed ped) {
        if (dir.equalsIgnoreCase(ped.getDirNorth()))
            return ped.getStreetinfoNorth();
        else if (dir.equalsIgnoreCase(ped.getDirEast()))
            return ped.getStreetinfoEast();
        else if (dir.equalsIgnoreCase(ped.getDirSouth()))
            return ped.getStreetinfoSouth();
        else if (dir.equalsIgnoreCase(ped.getDirWest()))
            return ped.getStreetinfoWest();
        else if (dir.equalsIgnoreCase(ped.getDirNortheast()))
            return ped.getStreetinfoNortheast();
        else if (dir.equalsIgnoreCase(ped.getDirSoutheast()))
            return ped.getStreetinfoSoutheast();
        else if (dir.equalsIgnoreCase(ped.getDirSouthwest()))
            return ped.getStreetinfoSouthwest();
        else if (dir.equalsIgnoreCase(ped.getDirNorthwest()))
            return ped.getStreetinfoNorthwest();
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_GAME);
        //sm.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_GAME);
        sm.registerListener(this, sm.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int test = Math.round(event.values[0]);
        // LogUtils.log("angle: " + test);
 /*       if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                azimuth = orientation[0]; // orientation contains: azimut, pitch and roll
            }
        }
        dirDeg = Math.toDegrees(azimuth);
        if (dirDeg < 0)
            dirDeg = 360 + dirDeg;*/

        dirDeg = test;
        direction.setText("Direction: " + (int) dirDeg);
        //LogUtils.log("azimuth is : " + (int) dirDeg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        // Be sure to call the superclass implementation
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        performSingleClick();
        LogUtils.log("Inside: onsingletapconfirmed");
        return true;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        performDoubleClick();
        LogUtils.log("Inside: ondoubletap");
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {


    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {

        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {


    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        return true;
    }

/*
    class TimerThread extends Thread {
        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(1000);
                    stopTime--;
                    if (flag == DBUtils.INTX) {
                        for (Map.Entry<String, Integer> entry : directionPhase.entrySet()) {
                            if (entry.getValue() != -1 && directionTimer.get(entry.getKey()) == -1) {
                                new Thread(new StopTimeRunnable(entry.getValue(), entry.getKey())).start();
                            }
                        }
                    }
                    if (stopTime == -1) {
                        timerHandler.sendEmptyMessage(1);
                        break;
                    } else {
                        timerHandler.sendEmptyMessage(0);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }*/

    class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            stopTime--;
            if (stopTime == -1) {
                timerHandler.sendEmptyMessage(1);
            } else {
                timerHandler.sendEmptyMessage(0);
            }
            if (flag == DBUtils.INTX) {
                for (Map.Entry<String, Integer> entry : directionPhase.entrySet()) {
                    if (entry.getValue() != -1) {
                        if (directionTimer.get(entry.getKey()) < 0) {
                            new Thread(new StopTimeRunnable(entry.getValue(), entry.getKey())).start();
                        } else {
                            directionTimer.put(entry.getKey(), directionTimer.get(entry.getKey()) - 1);
                            if (entry.getKey().equals(getCurrentDirection()) && directionTimer.get(entry.getKey()) == 5)
                                raiseWarning(1000, 5);
                        }
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String dir = getCurrentDirection();
                        if (directionTimer.get(dir) != null && directionTimer.get(dir) != -1)
                            parentlayout.setBackgroundColor(getResources().getColor(R.color.green));
                        else
                            parentlayout.setBackgroundColor(getResources().getColor(R.color.red));
                    }
                });
                for (Map.Entry entry : directionPhase.entrySet())
                    LogUtils.log("Phase direction: " + entry.getKey() + " : " + entry.getValue());
            }
        }
    }

    private void raiseWarning(int vibrationTime, int signalTime) {
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        v.vibrate(vibrationTime);
        switch (signalTime) {
            case 5:
                speak("5 seconds left");
                break;
            default:
                speak("Walk sign is on, " + signalTime + " seconds left");
                SCREEN_TIME_PERIOD = signalTime;
                stopTime = SCREEN_TIME_PERIOD;
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _instance = null;
    }

    public static NavigationActivity getInstance() {
        return _instance;
    }

    private class StopTimeRunnable implements Runnable {
        int phase;
        String dir;

        public StopTimeRunnable(int phase, String dir) {
            this.phase = phase;
            this.dir = dir;
        }

        @Override
        public void run() {
            if (intx_id != null) {
                String url = "http://128.101.111.92:8080/Pedestrian/pedestrian?table=get_signal_state&intx_id=" + intx_id + "&phase=" + phase;
                LogUtils.log(url);
                HttpURLConnection urlConnection = null;
                try {
                    urlConnection = (HttpURLConnection) new URL(url.toString()).openConnection();
                    urlConnection.setConnectTimeout(5000);
                    System.out.println((urlConnection.toString()));
                    InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    //********Reading Response From Server********
                    BufferedReader r = new BufferedReader(new InputStreamReader(in));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        sb.append(line);
                    }
                    String data = sb.toString();
                    directionTimer.put(dir, Integer.parseInt(data));
                    for (Map.Entry entry : directionTimer.entrySet())
                        LogUtils.log("Thread: " + entry.getKey() + ": " + entry.getValue());
                    System.out.println(data);
                    if (dir.equalsIgnoreCase(getCurrentDirection()) && Integer.parseInt(data) > 0)
                        raiseWarning(0, Integer.parseInt(data));
                    urlConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}