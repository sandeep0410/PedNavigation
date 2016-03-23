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
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.DecimalFormat;

import edu.umn.pednavigation.db.DBSQLiteHelper;
import edu.umn.pednavigation.db.DBUtils;
import edu.umn.pednavigation.dto.BLETag;
import edu.umn.pednavigation.dto.BluetoothDeviceObject;

public class NavigationActivity extends Activity implements SensorEventListener {
    double azimuth = 0.0;
    int flag = -1;
    String address = null;
    TextView timer;
    TextView direction;
    int stopTime = 60;
    private static NavigationActivity _instance;
    SensorManager sm;
    Sensor accelerometer;
    Sensor magnetometer;
    double dirDeg = 0;
    private AudioManager audiomanager;
    private TextToSpeech tts;
    Handler timerHandler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0)
                timer.setText(String.valueOf(stopTime));
            else
                finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.log("Oncreate");
        Intent i = new Intent(this, LocationService.class);
        startService(i);
        setContentView(R.layout.navigation_main);
        TextView mode = (TextView) findViewById(R.id.mode);
        timer = (TextView) findViewById(R.id.timer);
        direction = (TextView) findViewById(R.id.direction);
        timer.setText(String.valueOf(stopTime));
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", -1);
        address = intent.getStringExtra("bleAddress");
        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layout);
        sm = (SensorManager) getSystemService(SENSOR_SERVICE);
        accelerometer = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        magnetometer = sm.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSingleClick();
            }
        });
        layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                performLongClick();
                return true;
            }
        });
        (new TimerThread()).start();
        _instance = this;

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

    private void performLongClick() {
        if (flag == DBUtils.CONS) {
            DBSQLiteHelper db = DBSQLiteHelper.getInstance(this);
            BLETag ble = db.getBleTag(address);
            speak(ble.getMessage());

        } else if (flag == DBUtils.INTX) {

        }
        LogUtils.log("Single Tap Confirmed " + stopTime);
        stopTime = 60;
    }

    private void performSingleClick() {
        if (flag == DBUtils.CONS) {
            DBSQLiteHelper db = DBSQLiteHelper.getInstance(this);
            BLETag ble = db.getBleTag(address);
            speak(ble.getMessage());
        } else if (flag == DBUtils.INTX) {

        }
        LogUtils.log("Long Tap Confirmed " + stopTime);
        stopTime = 60;
    }

    @Override
    protected void onResume() {
        super.onResume();
        super.onResume();
        sm.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
        sm.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sm.unregisterListener(this);
    }

    // Chen-Fu added, 4/5/2012, get direction code string
    String getDirectionCode() {
        String dirStr = " ";
        if ((dirDeg > 348.75 && dirDeg <= 360) || (dirDeg >= 0 && dirDeg <= 11.25)) {
            dirStr = " north ";
        } else if (dirDeg > 11.25 && dirDeg <= 33.75) {
            dirStr = " north east north ";
        } else if (dirDeg > 33.75 && dirDeg <= 56.25) {
            dirStr = " north east ";
        } else if (dirDeg > 56.25 && dirDeg <= 78.75) {
            dirStr = " north east east ";
        } else if (dirDeg > 78.75 && dirDeg <= 101.25) {
            dirStr = " east ";
        } else if (dirDeg > 101.25 && dirDeg <= 123.75) {
            dirStr = " south east east ";
        } else if (dirDeg > 123.75 && dirDeg <= 146.25) {
            dirStr = " south east ";
        } else if (dirDeg > 146.25 && dirDeg <= 168.75) {
            dirStr = " south east south ";
        } else if (dirDeg > 168.75 && dirDeg <= 191.25) {
            dirStr = " south ";
        } else if (dirDeg > 191.25 && dirDeg <= 213.75) {
            dirStr = " south west south ";
        } else if (dirDeg > 213.75 && dirDeg <= 236.25) {
            dirStr = " south west ";
        } else if (dirDeg > 236.25 && dirDeg <= 258.75) {
            dirStr = " south west west ";
        } else if (dirDeg > 258.75 && dirDeg <= 281.25) {
            dirStr = " south ";
        } else if (dirDeg > 281.25 && dirDeg <= 303.75) {
            dirStr = " north west west ";
        } else if (dirDeg > 303.75 && dirDeg <= 326.25) {
            dirStr = " north west ";
        } else if (dirDeg > 326.25 && dirDeg <= 348.75) {
            dirStr = " north west north ";
        }
        return dirStr;
    }

    float[] mGravity;
    float[] mGeomagnetic;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
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
            dirDeg = 360 + dirDeg;
        DecimalFormat df = new DecimalFormat("#.##");
        direction.setText("Direction: " + df.format(dirDeg));
        LogUtils.log("azimuth is : " + df.format(dirDeg));
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    class TimerThread extends Thread {
        @Override
        public void run() {
            while (true) {

                try {
                    Thread.sleep(1000);
                    stopTime--;
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _instance = null;
    }

    public static NavigationActivity getInstance() {
        return _instance;
    }
}