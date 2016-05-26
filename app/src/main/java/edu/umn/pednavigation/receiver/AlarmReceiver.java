package edu.umn.pednavigation.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.umn.pednavigation.LogUtils;
import edu.umn.pednavigation.Util;
import edu.umn.pednavigation.db.DBUtils;
import edu.umn.pednavigation.db.DbDownloader;


/**
 * Created by Sandeep on 10/9/2015.
 */
public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction()!=null && intent.getAction().equals("edu.umn.pednavigation.DOWNLOAD.START"))
            LogUtils.log("Received Broadcast");
        if (Util.isOnline(context)) {
            ExecutorService pool = Executors.newSingleThreadExecutor();
            pool.submit(new DbDownloader(context, DBUtils.BLEPN_TABLE));
            pool.submit(new DbDownloader(context, DBUtils.BLEFLAG_TABLE));
            pool.submit(new DbDownloader(context, DBUtils.BLETAG_TABLE));
            pool.submit(new DbDownloader(context, DBUtils.BLEPHASE_TABLE));
        }

    }


}