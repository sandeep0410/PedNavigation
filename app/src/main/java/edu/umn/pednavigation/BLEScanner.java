package edu.umn.pednavigation;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Handler;
import android.os.Message;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umn.pednavigation.db.DBSQLiteHelper;
import edu.umn.pednavigation.db.DBUtils;
import edu.umn.pednavigation.dto.BLEFlag;
import edu.umn.pednavigation.dto.BLETag;
import edu.umn.pednavigation.dto.BluetoothDeviceObject;
import edu.umn.pednavigation.settings.Settings;

/**
 * Created by Sandeep on 12/24/2015.
 */
public class BLEScanner {

    public static final String DIR_NAME = "MTO_BLE";
    private static final int STOP_SCANNING = 1001;
    private static final long SCAN_PERIOD = 100000;
    private static final int REQUEST_ENABLE_BT = 1;
    private static BLEScanner _instance;
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothLeScanner mScanner;
    private boolean scanning = false;
    private boolean bleSupported = true;
    private ScanCallback mLatestScanCallback;
    private Map<String, BluetoothDeviceObject> scannedDevices = new HashMap<String, BluetoothDeviceObject>();
    private Context mContext;
    private TextToSpeech tts;
    private AudioManager audiomanager;
    private Handler mHandler;
    private Thread stopper = null;

    private Handler messageHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == STOP_SCANNING) {
                analyzeDevice();
            }
        }
    };

    private void analyzeDevice() {
        scanning = false;
        StopScanForLatestAndroid();
        BluetoothDeviceObject device = null;
        for (Map.Entry<String, BluetoothDeviceObject> entry : scannedDevices.entrySet()) {
            if (device == null)
                device = entry.getValue();
            else if (device.getRssi() < entry.getValue().getRssi())
                device = entry.getValue();
        }
        if (device != null) {
            DBSQLiteHelper db = DBSQLiteHelper.getInstance(mContext);
            BLEFlag ble = db.getBleFlag(device.getDevice().getAddress());
            Intent i = new Intent(mContext, NavigationActivity.class);
            i.putExtra("bleAddress", device.getDevice().getAddress());
            if (ble.getFlag() == DBUtils.CONS) {
                speak(device);
                i.putExtra("flag", DBUtils.CONS);
            } else if (ble.getFlag() == DBUtils.INTX) {
                i.putExtra("flag", DBUtils.INTX);
            } else {
                Toast.makeText(mContext, "Data Not found in the database", Toast.LENGTH_SHORT).show();
            }
            mContext.startActivity(i);
        }

    }

    public static BLEScanner getInstance(Context context) {
        if (_instance == null)
            _instance = new BLEScanner(context);
        return _instance;
    }

    public BLEScanner(Context context) {
        mContext = context;
        mHandler = new Handler();
        if (!context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            bleSupported = false;
            return;
        }
        final BluetoothManager bluetoothManager = (BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            bleSupported = false;
            return;
        }

        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            enableBtIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(enableBtIntent);
        }
        initializeLatestScanCallBack();

        _instance = this;
        initializeAudioItems();
    }

    private void initializeAudioItems() {
        audiomanager = (AudioManager) mContext.getSystemService(Context.AUDIO_SERVICE);
        tts = new TextToSpeech(mContext, new TextToSpeech.OnInitListener() {

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

    public boolean isScanning() {
        return scanning;
    }

    public boolean isBLESupported() {
        return bleSupported;
    }

    private void initializeLatestScanCallBack() {
        mScanner = mBluetoothAdapter.getBluetoothLeScanner();
        mLatestScanCallback = new ScanCallback() {
            @Override
            public void onScanResult(int callbackType, ScanResult result) {
                final BluetoothDevice device = result.getDevice();
                int rssi = result.getRssi();
                if (device == null)
                    return;
                if (rssi < (-1 * Settings.rssi_value))
                    return;
                if (null == device.getName() || !device.getName().startsWith("MTO"))
                    return;
                scannedDevices.put(device.getName(), new BluetoothDeviceObject(device, rssi));
                if (stopper == null) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            messageHandler.sendEmptyMessage(STOP_SCANNING);
                        }
                    }).start();
                }

            }
        };

    }


    private void speak(BluetoothDeviceObject device) {
        DBSQLiteHelper db = DBSQLiteHelper.getInstance(mContext);
        BLETag ble = db.getBleTag(device.getDevice().getAddress());
        if(ble == null)
            return;
        String msg = ble.getMessage();
        if (!tts.isSpeaking()) {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, "" + audiomanager.getStreamVolume(AudioManager.STREAM_MUSIC));
        }
    }

    public void scanLeDevice(final boolean enable) {
        if (enable) {
            scannedDevices.clear();
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopScanningforDevices();
                }
            }, SCAN_PERIOD);

            scanning = true;
            StartScanForLatestAndroid();

            // if (Settings.data_collection)
            //startWritingService();
        } else {
            stopScanningforDevices();
        }
    }


    private void StartScanForLatestAndroid() {
        if (null != mScanner) {
            ScanSettings settings = new ScanSettings.Builder()
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .build();
            List<ScanFilter> filters = new ArrayList<ScanFilter>();
            mScanner.startScan(filters, settings, mLatestScanCallback);
            LogUtils.writeToFile("started");
        }
    }

    private void StopScanForLatestAndroid() {
        if (null != mScanner) {
            mScanner.stopScan(mLatestScanCallback);
            LogUtils.writeToFile("stopped");
        }
    }

    /*Stop Scanning for Bluetooth Devices*/
    private void stopScanningforDevices() {
        scanning = false;
        StopScanForLatestAndroid();
        _instance = null;
    }

}
